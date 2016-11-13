package compiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import soot.Unit;
import soot.Value;

public class CompileResult {
	
	    	Value place;
	    	List<Unit> code = new ArrayList<Unit>();
	    	Set<Unit> backpath = new HashSet<Unit>();
}
