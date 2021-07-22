package com.Model;

import com.Entity.Pass;

public interface PassDAO {

	public boolean insertPass(Pass pass);

	public Pass login(String username, String password);

}
