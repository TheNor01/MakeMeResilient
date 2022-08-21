package com.parser;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import static com.github.javaparser.StaticJavaParser.parseBlock;
import static com.github.javaparser.StaticJavaParser.parseStatement;

public class VisitorExample {

	private static final String FILE_PATH = "/Users/thenor/Desktop/Aldo/MakeMeResilient/src/main/java/com/client/Proxy.java";
	
	public static void main(String[] args) throws Exception{
		CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));


		List<TypeDeclaration<?>> typeDeclarations = cu.getTypes();
		for (TypeDeclaration typeDec : typeDeclarations) {
			System.out.println(typeDec.getName());

			List<MethodDeclaration> members = typeDec.getMethods();
			List<ConstructorDeclaration> constructors = typeDec.getConstructors();
			System.out.println(constructors);
			System.out.println(members);

			for(ConstructorDeclaration c : constructors){
				BlockStmt body = c.getBody();
				String m = String.format( "%s::%s( %s )",
						typeDec.getNameAsString(),
						c.getNameAsString(),
						c.getParameters().toString() );

				System.out.println("FORMAT: "+m);
				final String mBegan = String.format(
						"System.out.println(\"BEGAN %s\");", m );
				final String mEnded = String.format(
						"System.out.println(\"ENDED %s\");", m );

				final Statement sBegan = parseStatement( mBegan );
				final Statement sEnded = parseStatement( mEnded );

				System.out.println(sBegan);
				System.out.println(sEnded);

				body.ifBlockStmt(( b ) -> {
					NodeList<Statement> statements= b.getStatements();
					System.out.println(statements.size());
					b.addStatement( 0, sBegan );

					Statement statement = parseStatement("System.out.println(\"hello there\");");
					b.addStatement(1,statement);

					statements.forEach(s-> {
						if(s.isTryStmt()){
							s.findAll(AssignExpr.class).forEach(ae->{
								Expression e = ae.getValue();
								System.out.println(e.toString());
							});
						}

					});

				} );
			}
		}

		System.out.println("\n\n CALLING VISITOR");

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
		public void visit(ConstructorDeclaration cd,Void arg){
			super.visit(cd,arg);
			cd.findAll(ConstructorDeclaration.class).forEach(c->
					System.out.println(c.getBody())
			);
		}

		public void visit(MethodDeclaration md, Void arg) {
			super.visit(md,  arg);
			System.out.println("Method Name Printed: " + md.getName());

			md.findAll(AssignExpr.class).forEach(m->
							System.out.println(m.getTarget())
					);

			md.findAll(MethodDeclaration.class).forEach(m -> {
				NodeList<Parameter> mParameters = m.getParameters();
				mParameters.forEach(parameter -> System.out.println(parameter.getType()));

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