package de.stevenschwenke.java.testbestpracticesandtoolsworkshop;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchitectureRulesTest {

    @Test
    public void projectOnPackageLevelShouldBeFreeOfCycles() {

        JavaClasses importedClasses = new ClassFileImporter().importPackages("de.volkswagen.okapi.admin");

        slices()
                .matching("..(de.stevenschwenke.java.testbestpracticesandtoolsworkshop).(**)")
                .should()
                .beFreeOfCycles()
                .check(importedClasses);
    }

    @Test
    public void servicesOnlyAccessedByControllersAndOtherServices() {

        JavaClasses importedClasses = new ClassFileImporter().importPackages("de.stevenschwenke.java.testbestpracticesandtoolsworkshop");
        ArchRule myRule = classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");
        myRule.check(importedClasses);
    }

//    @Test
//    public void disjunctPackages() {
//        JavaClasses importedClasses = new ClassFileImporter().importPackages("de.stevenschwenke.java.testbestpracticesandtoolsworkshop");
//        SliceRule sliceRule = slices().matching("de.stevenschwenke.java.testbestpracticesandtoolsworkshop.(*)..").should().notDependOnEachOther()
//                .ignoreDependency(DescribedPredicate.alwaysTrue(), nameMatching("de.stevenschwenke.java.testbestpracticesandtoolsworkshop.authentication..*"));
//
//        sliceRule.check(importedClasses);
//    }
}
