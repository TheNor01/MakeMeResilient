package com.parser;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VisitorExample {

	private static final String FILE_PATH = "D:\\ALDO\\Neodata\\repos\\maven-tutorial\\src\\main\\java\\com\\client\\App.java";
	
	public static void main(String[] args) throws Exception{
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));

		// FIRST TYPE //
		VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter();
		methodNameVisitor.visit(cu, null);


		System.exit(0);

		// SECOND TYPE //
		List<String> methodNames = new ArrayList<>();
		VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
		methodNameCollector.visit(cu, methodNames);
		methodNames.forEach(n -> System.out.println("Method Name Collected: " + n));



		// THIRD TYPE
		ModifierVisitor<?> methodNameModifier = new MethodNameModifier();
		methodNameModifier.visit(cu, null);
		
		System.out.println("\n\n" + cu);
	}
	
	private static class MethodNamePrinter extends VoidVisitorAdapter<Void>{
		
		@Override
		public void visit(MethodDeclaration md, Void arg) {
			super.visit(md,  arg);
			System.out.println("Method Name Printed: " + md.getName());
			md.findAll(MethodCallExpr.class).forEach(m -> {
				System.out.println(m);
			});
			System.out.println("\n");
		}
	}
	
	private static class MethodNameCollector extends VoidVisitorAdapter<List<String>>{
		
		@Override
		public void visit(MethodDeclaration md, List<String> collector) {
			super.visit(md,  collector);
			collector.add(md.getNameAsString());
		}
	}
	
	private static class MethodNameModifier extends ModifierVisitor<Void>{
		
		@Override
		public MethodDeclaration visit(MethodDeclaration md, Void arg) {
			super.visit(md, arg);
			md.setName("NewName");
			return md;
		}
	}
}