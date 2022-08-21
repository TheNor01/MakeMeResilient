package com.parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LexicalPreservingExample {

    private static CompilationUnit cu = new CompilationUnit();


    public static void createClassDummy(){
        ClassOrInterfaceDeclaration myClass;
        myClass = cu.addClass("myClassParser");
        myClass.setName("myClassParser");
        myClass.addModifier(Modifier.Keyword.PUBLIC);
        myClass.addField("Integer","foo");
        myClass.addField("String","boo") ;
        myClass.addConstructor(Modifier.Keyword.PUBLIC)
                .addParameter("String","booLocal")
                .addParameter("Integer","fooLocal")
                .setBody(new BlockStmt()
                        .addStatement(  new ExpressionStmt(new AssignExpr(
                                new FieldAccessExpr(new ThisExpr(),"foo"),
                                new NameExpr("fooLocal"),AssignExpr.Operator.ASSIGN)))

                        .addStatement(  new ExpressionStmt(new AssignExpr(
                                new FieldAccessExpr(new ThisExpr(),"boo"),
                                new NameExpr("booLocal"),AssignExpr.Operator.ASSIGN))));

        myClass.addMethod("getFoo",Modifier.Keyword.PUBLIC)
                .setBody(
                        new BlockStmt().addStatement(new ReturnStmt(new NameExpr("foo")))
                ).setType("Integer");
    }


	public static void main(String[] args) throws IOException {
        //String code = "// Hey, this is a comment\n\n\n// Another one\n\n class TestMyClass { }";
        //CompilationUnit cu = StaticJavaParser.parse(code);


        cu.addImport("java.util.concurrent.TimeUnit");
        LexicalPreservingPrinter.setup(cu);

        createClassDummy();


        System.out.println("----------------");
        cu.setPackageDeclaration("com.parser");
        System.out.println(LexicalPreservingPrinter.print(cu));

        File classFile = new File("/Users/thenor/Desktop/Aldo/MakeMeResilient/src/main/java/com/parser/myClassParser.java");
        classFile.createNewFile();
        System.out.println(classFile.getPath() + " created successfully...");
        FileWriter fr = null;
        try{
                fr = new FileWriter(classFile);
                fr.write(cu.toString());
        }
        catch (IOException e){
                e.printStackTrace();
        }finally {
                assert fr != null;
                fr.close();
        }
    }
}