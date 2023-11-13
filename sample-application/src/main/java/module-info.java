module spring.boot.cd.skeleton.sample.application.main {

    requires spring.boot.cd.skeleton.sample.model.main;

    requires org.apache.commons.lang3;
    requires com.google.common;
    requires org.slf4j;

    requires static lombok;

    requires spring.beans;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires org.apache.tomcat.embed.core;

    opens org.bananalaba.springcdtemplate to spring.core;
    exports org.bananalaba.springcdtemplate;

    opens org.bananalaba.springcdtemplate.web to spring.core;
    exports org.bananalaba.springcdtemplate.web;
}
