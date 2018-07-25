package com.mukund.structure.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mukund.structure.exception.WorkflowException;
import com.mukund.structure.model.Book;
import com.mukund.structure.model.RequestStatusE;
import com.mukund.structure.model.WorkflowBook;
import com.mukund.structure.model.WorkflowBook.WorkFlowVariable;
import com.mukund.structure.model.WorkflowE;

@Service
public class WorkflowServiceImpl implements WorkflowService {
	private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

	@Autowired
	RuntimeService runtSrvc;

	@Autowired
	HistoryService histSrvc;

	@Autowired
	TaskService taskSrvc;

	@Autowired
	RepositoryService repoSrvc;

	@Override
	public WorkflowBook startWorkflow(Book book) {
		// Wrap book to workflow book
		WorkflowBook b = new WorkflowBook(book);
		b.setRequestStatus(RequestStatusE.PENDING_APPROVAL.val());

		ProcessDefinition procDef = findProcDef();
		if (procDef == null) {
			throw new WorkflowException("No workflow exists for given name=" + WorkflowE.PROCESS_ID.val());
		}

		// If Workflow object already exist for book id then use it else add new
		// object
		ProcessInstance current = runtSrvc.createProcessInstanceQuery().processInstanceBusinessKey(b.getId().toString())
				.singleResult();
		if (current == null) {
			current = runtSrvc.startProcessInstanceByKey(procDef.getKey(), b.getId().toString(), b.getWorkFlowParam());
		}
		runtSrvc.addParticipantUser(current.getId(), b.getRequester());

		return (WorkflowBook) runtSrvc.getVariable(current.getId(), WorkFlowVariable.OBJECT);
	}

	@Override
	public void cancelWorkflow(String user, Book book) {
		try {
			ProcessInstance processInstance = this.runtSrvc.createProcessInstanceQuery()
					.processInstanceId(book.getId().toString()).singleResult();
			if (processInstance == null) {
				throw new WorkflowException("Process not found for given key: " + book.getId().toString());
			}

			// Delete the process instance
			this.runtSrvc.deleteProcessInstance(processInstance.getId(), WorkflowE.PROP_CANCELLED.val());

			// Convert historic process instance
			this.histSrvc.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId())
					.singleResult();
		} catch (ActivitiException ae) {
			throw new WorkflowException("Exception while canceling request", ae);
		}
	}

	@Override
	public void deleteWorkflow(String user, Book book) {

		try {
			List<ProcessInstance> processes = runtSrvc.createProcessInstanceQuery()
					.processInstanceBusinessKey(book.getId().toString()).active().list();

			// if book is inactive then delete from history only
			if (processes.isEmpty()) {
				List<HistoricProcessInstance> instances = histSrvc.createHistoricProcessInstanceQuery()
						.processInstanceBusinessKey(book.getId().toString()).list();
				instances.stream().forEach(hist -> {
					// Delete the historic process instance
					histSrvc.deleteHistoricProcessInstance(hist.getId());
				});
			}

			// Delete the process instance
			processes.stream().forEach(proc -> {
				runtSrvc.deleteProcessInstance(proc.getId(), WorkflowE.PROP_DELETED.val());

				List<HistoricProcessInstance> instances = histSrvc.createHistoricProcessInstanceQuery()
						.processInstanceId(proc.getId()).list();
				instances.stream().forEach(hist -> {
					// Delete the historic process instance
					histSrvc.deleteHistoricProcessInstance(hist.getId());
				});

			});
		} catch (ActivitiException ae) {
			throw new WorkflowException("Exception while deleting request", ae);
		}
	}

	@Override
	public String approve(String userId, Book book) {
		Task task = taskSrvc.createTaskQuery().processInstanceBusinessKey(book.getId().toString())
				.taskDefinitionKey(WorkflowE.APPROVAL_TASK_ID.val()).taskAssignee(userId).active().singleResult();
		// Complete user task if any pending
		if (task != null && !taskSrvc.createTaskQuery().taskId(task.getId()).list().isEmpty()) {
			Map<String, Object> var = new HashMap<>();
			var.put(WorkflowE.TASK_STATUS.val(), RequestStatusE.APPROVED.val());
			taskSrvc.complete(task.getId(), var);
			return task.getAssignee();
		}
		return null;
	}

	@Override
	public String reject(String userId, Book book) {
		try {
			ProcessInstance process = runtSrvc.createProcessInstanceQuery()
					.processInstanceBusinessKey(book.getId().toString()).active().singleResult();

			// Delete the process instance
			runtSrvc.deleteProcessInstance(process.getId(), RequestStatusE.REJECTED.val());
			return process.getBusinessKey();
		} catch (ActivitiException ae) {
			throw new WorkflowException("Exception while rejecting request", ae);
		}
	}

	@Override
	public String updateRequire(String userId, Book book) {
		Task task = taskSrvc.createTaskQuery().processInstanceBusinessKey(book.getId().toString())
				.taskName(WorkflowE.APPROVAL_TASK_ID.val()).taskAssignee(userId).active().singleResult();

		// Complete user task if any pending
		if (!taskSrvc.createTaskQuery().taskId(task.getId()).active().list().isEmpty()) {
			Map<String, Object> var = new HashMap<>();
			var.put(WorkflowE.TASK_STATUS.val(), RequestStatusE.UPDATE_REQUIRE.val());
			taskSrvc.complete(task.getId(), var);
		}
		return "" + task.getProcessVariables().get(WorkFlowVariable.OBJECT);
	}

	public void createHistory() {
		// historyService.createHistoricVariableInstanceQuery().processInstanceId("processInstanceId").variableName("variableName").singleResult().getValue();

	}

	@Override
	public byte[] generateWorkflowDiagram(Book book) throws IOException {
		ProcessDefinition pd = this.repoSrvc.createProcessDefinitionQuery()
				.processDefinitionKey(book.getId().toString()).latestVersion().singleResult();
		logger.debug("Getting process diagram for processId: {}", pd.getId());
		BpmnModel bpmnModel = repoSrvc.getBpmnModel(pd.getId());
		new BpmnAutoLayout(bpmnModel).execute();
		InputStream in = new DefaultProcessDiagramGenerator().generatePngDiagram(bpmnModel);

		// InputStream in = new
		// DefaultProcessDiagramGenerator().generatePngDiagram(model);
		byte[] bytes = IOUtils.toByteArray(in);
		IOUtils.closeQuietly(in);
		logger.debug("Got bytes of size: {}", bytes.length);
		return bytes;
	}

	@Override
	public byte[] generateActiveBookDiagram(Book book) throws IOException {
		logger.debug("getting active diagram for doc: {}", book.getId());
		// http://forums.activiti.org/content/process-diagram-highlighting-current-process
		ProcessInstance pi = runtSrvc.createProcessInstanceQuery().processInstanceBusinessKey(book.getId().toString())
				.active().singleResult();
		RepositoryServiceImpl impl = (RepositoryServiceImpl) repoSrvc;
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) impl
				.getDeployedProcessDefinition(pi.getProcessDefinitionId());
		BpmnModel bpmnModel = repoSrvc.getBpmnModel(pde.getId());
		new BpmnAutoLayout(bpmnModel).execute();
		InputStream in = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, "png",
				runtSrvc.getActiveActivityIds(pi.getProcessInstanceId()));
		// InputStream in =
		// this.appContext.getResource("classpath:800x200.png").getInputStream();
		byte[] bytes = IOUtils.toByteArray(in);
		IOUtils.closeQuietly(in);
		logger.debug("Got bytes of size: " + bytes.length);
		return bytes;
	}

	@Override
	public List<WorkflowBook> findWorkFlowBookById(Book book, boolean onlyActive) {

		if (onlyActive)
			return findActiveWorkFlowBookById(book);

		List<HistoricProcessInstance> processes = histSrvc.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(book.getId().toString()).includeProcessVariables().list();
		List<WorkflowBook> books = new ArrayList<>();
		processes.stream().forEach(proc -> {
			books.add((WorkflowBook) proc.getProcessVariables().get(WorkFlowVariable.OBJECT));
		});

		return books;
	}

	@Override
	public List<WorkflowBook> findWorkFlowBookByUserId(String userId, boolean onlyActive) {

		if (onlyActive)
			return findActiveWorkFlowBookByUserId(userId);

		List<HistoricProcessInstance> processes = histSrvc.createHistoricProcessInstanceQuery().involvedUser(userId)
				.includeProcessVariables().list();
		List<WorkflowBook> books = new ArrayList<>();
		processes.stream().forEach(proc -> {
			books.add((WorkflowBook) proc.getProcessVariables().get(WorkFlowVariable.OBJECT));
		});
		return books;
	}

	@Override
	public List<WorkflowBook> findApprovalWorkFlowBookByUserId(String userId) {
		List<WorkflowBook> books = new ArrayList<>();
		List<Task> tasks = taskSrvc.createTaskQuery().taskDefinitionKey(WorkflowE.APPROVAL_TASK_ID.val())
				.taskAssignee(userId).active().includeProcessVariables().list();
		tasks.stream().forEach(task -> {
			books.add((WorkflowBook) task.getProcessVariables().get(WorkFlowVariable.OBJECT));
		});
		return books;
	}

	@Override
	public List<WorkflowBook> fetchWorkFlowBooks(boolean requireActive) {
		// fetch only active books
		if (requireActive)
			return fetchActiveWorkFlowBooks();

		List<WorkflowBook> books = new ArrayList<>();
		HistoricProcessInstanceQuery query = histSrvc.createHistoricProcessInstanceQuery().includeProcessVariables();
		List<HistoricProcessInstance> processes = query.list();
		processes.stream().forEach(proc -> {
			books.add((WorkflowBook) proc.getProcessVariables().get(WorkFlowVariable.OBJECT));
		});

		return books;
	}

	/**
	 * 
	 * @return
	 */
	private List<WorkflowBook> fetchActiveWorkFlowBooks() {
		List<WorkflowBook> books = new ArrayList<>();
		List<ProcessInstance> processes = runtSrvc.createProcessInstanceQuery().active().includeProcessVariables()
				.list();
		processes.stream().forEach(proc -> {
			books.add((WorkflowBook) proc.getProcessVariables().get(WorkFlowVariable.OBJECT));
		});
		return books;
	}

	private List<WorkflowBook> findActiveWorkFlowBookById(Book book) {
		List<WorkflowBook> books = new ArrayList<>();
		List<ProcessInstance> tasks = runtSrvc.createProcessInstanceQuery()
				.processInstanceBusinessKey(book.getId().toString()).active().includeProcessVariables().list();
		tasks.stream().forEach(task -> {
			books.add((WorkflowBook) task.getProcessVariables().get(WorkFlowVariable.OBJECT));
		});

		return books;
	}

	private List<WorkflowBook> findActiveWorkFlowBookByUserId(String userId) {
		List<WorkflowBook> books = new ArrayList<>();
		List<ProcessInstance> tasks = runtSrvc.createProcessInstanceQuery().involvedUser(userId).active()
				.includeProcessVariables().list();
		tasks.stream().forEach(task -> {
			books.add((WorkflowBook) task.getProcessVariables().get(WorkFlowVariable.OBJECT));
		});

		return books;
	}

	/**
	 * this should be protected by admin only
	 * 
	 * @param onlyLatestVersion
	 * @return
	 */

	private List<ProcessDefinition> getAllProcDefs(boolean onlyLatestVersion) {
		logger.debug("Lookign up all process definitions with latestVersion={}", onlyLatestVersion);
		ProcessDefinitionQuery query = repoSrvc.createProcessDefinitionQuery()
				.processDefinitionCategory(WorkflowE.NAMESPACE.val());
		if (onlyLatestVersion) {
			query.latestVersion();
		}
		List<ProcessDefinition> definitions = query.list();
		logger.debug("Found {} definitions.", definitions.size());
		return definitions;
	}

	/**
	 * <p/>
	 * This is a convenience method that will try for the most specific workflow
	 * (group and docType), but will fall back to just general docType if no
	 * group workflow exists.
	 *
	 * @param docType
	 * @return latest process definition for the given group and/or docType or
	 *         null if neither (i.e. no group and also no docType) exits.
	 */
	private ProcessDefinition findProcDef() {
		ProcessDefinition pd = this.repoSrvc.createProcessDefinitionQuery()
				.processDefinitionCategory(WorkflowE.NAMESPACE.val()).processDefinitionKey(WorkflowE.PROCESS_ID.val())
				.latestVersion().singleResult();
		return pd;
	}

}
