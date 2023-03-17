package com.quantum.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import java.lang.reflect.Field;

@Service
public class RoleService 
{
	public List<String> getFields(Object o) 
	{
		List<String> attributeList = new ArrayList<String>();
		
	   Class<?> clazz = o.getClass();

	   for(Field field : clazz.getDeclaredFields()) 
	   {
		   //you can also use .toGenericString() instead of .getName(). This will
		   //give you the type information as well.

		   attributeList.add(field.getName());
	   }
	   
	   return attributeList;
	}
}
