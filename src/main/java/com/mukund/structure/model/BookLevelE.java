package com.mukund.structure.model;

import java.util.Arrays;

import com.google.common.collect.ImmutableList;

public enum BookLevelE {
	INVALID,
	SP1, SP2, SP3, SP4, 
	LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5, LEVEL6, LEVEL7, LEVEL8, LEVEL9, LEVEL10, LEVEL11, 
	JPLvL1, JPLvL2, JPLvL3, JPLvL4, JPLvL5, JPLvL6, JPLvL7;
	
	public final static ImmutableList<BookLevelE> RDM_LEVEL = ImmutableList.of(SP1, SP2, SP3, SP4);
	public final static ImmutableList<BookLevelE> GFA_LEVEL = ImmutableList.of(JPLvL1, JPLvL2, JPLvL3, JPLvL4, JPLvL5, JPLvL6, JPLvL7);
	public final static ImmutableList<BookLevelE> GBS_LEVEL = ImmutableList.of(LEVEL1, LEVEL2, LEVEL3, LEVEL4, LEVEL5, LEVEL6, LEVEL7,LEVEL8,LEVEL9,LEVEL10,LEVEL11);
	
	public final static BookLevelE RDM_CREATION = LEVEL8;
	public final static BookLevelE GFA_CREATION = LEVEL5;
	
	public boolean allow(HierarchyE hier) {
			if(!GBS_LEVEL.contains(this)) return false;
			
			if(hier==HierarchyE.RDM){
				return GBS_LEVEL.indexOf(this) >= GBS_LEVEL.indexOf(RDM_CREATION);
			}else if(hier==HierarchyE.GFA){
				return GBS_LEVEL.indexOf(this) >= GBS_LEVEL.indexOf(GFA_CREATION);
			}
		return false;
	}
	
	
	public BookLevelE next() {
		if(RDM_LEVEL.contains(this)){
			return RDM_LEVEL.indexOf(this) < RDM_LEVEL.size() - 1 ? RDM_LEVEL.get(RDM_LEVEL.indexOf(this) + 1) : INVALID;
		}else if(GFA_LEVEL.contains(this)){
			return GFA_LEVEL.indexOf(this) < GFA_LEVEL.size() - 1 ? GFA_LEVEL.get(GFA_LEVEL.indexOf(this) + 1) : INVALID;
		}else{
			return GBS_LEVEL.indexOf(this) < GBS_LEVEL.size() - 1 ? GBS_LEVEL.get(GBS_LEVEL.indexOf(this) + 1) : INVALID;
		}
	}
	
	public static BookLevelE enumOf(String val) {
		return Arrays.stream(BookLevelE.values()).filter(v -> v.name().equalsIgnoreCase(val)).distinct()
				.findAny().get();
	}

}
