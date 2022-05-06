package com.olsystem.spring_curso.resources.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static List<Integer> decodeIntList(String s) {

//FORMA TRADICIONAL		

//		String[] vet = s.split(",");
//		List<Integer> list = new ArrayList<>();
//		for (int i = 0; i < vet.length; i++) {
//			Integer.parseInt(vet[i]);
//			list.add(Integer.parseInt(vet[i]));
//
//		}
//
//		return list;

// UTILIZANDO UMA EXPRESSÃO LAMBDA

		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());

	}
}
