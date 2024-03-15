package com.pivovarit;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.pivovarit", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule publicClassesInRootPackageShouldBeCalledFacade = classes()
      .that()
      .arePublic()
      .and()
      .resideInAnyPackage("..rental", "..account")
      .should()
      .haveSimpleNameEndingWith("Facade")
      .as("public classes in root packages should have *Facade name");

    @ArchTest
    public static final ArchRule warehouseInternalClassesShouldBeAccessedByPackageOnly =
      classes()
        .that()
        .resideInAPackage("..warehouse..")
        .and()
        .arePublic()
        .and()
        .haveSimpleNameNotEndingWith("Facade")
        .should()
        .onlyHaveDependentClassesThat()
        .resideInAPackage("..warehouse..");
}
