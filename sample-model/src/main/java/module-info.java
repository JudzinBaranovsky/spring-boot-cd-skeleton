module spring.boot.cd.skeleton.sample.model.main {

    requires org.apache.commons.lang3;
    requires com.google.common;
    requires com.fasterxml.jackson.databind;

    requires static lombok;

    exports org.bananalaba.springcdtemplate.model;

}
