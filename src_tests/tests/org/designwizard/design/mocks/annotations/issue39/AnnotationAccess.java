package tests.org.designwizard.design.mocks.annotations.issue39;

import java.util.HashSet;
import java.util.Set;

import tests.org.designwizard.design.mocks.annotations.external.AnnotationA;
import tests.org.designwizard.design.mocks.annotations.external.AnnotationB;

public class AnnotationAccess {

    private Set<Class> loadExternalAnnotation() {
        Set<Class> set = new HashSet<Class>();

        Class<AnnotationA> annotationA = AnnotationA.class;
        Class<AnnotationB> annotationB = AnnotationB.class;
        Class<AnnotationF> annotationF = AnnotationF.class;
        Class<AnnotationG> annotationG = AnnotationG.class;

        //External Annotation and unused.
        set.add(annotationA);

        //External Annotation and used.
        set.add(annotationB);

        //Internal Annotation and used.
        set.add(annotationF);

        //Internal Annotation and unused.
        set.add(annotationG);

        return set;
    }

    public static void main(String[] args) {

        AnnotationAccess access = new AnnotationAccess();

        Set<Class> annotations = access.loadExternalAnnotation();

        for (Class annotation : annotations) {
            System.out.println(annotation.getCanonicalName());
        }
    }
}
