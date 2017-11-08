package com.dhbwGroup.kanban.unitTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.dhbwGroup.kanban.controllers.Controller;

class TestMainController {

	@Test
	void test() {
		try {
			Controller controller = new Controller();
			
			Method method = Controller.class.getDeclaredMethod("initializeMenu", Controller.class);
			method.setAccessible(true);
			method.invoke(controller);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
