package springboot.vertx.mysql.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import springboot.vertx.mysql.starter.annotation.VertxDao;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

/**
 * @author iheng
 * @date 1/26/18
 */
@Slf4j
public class VertxMapperScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar,
        ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private ClassLoader classLoader;
    private Environment environment;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        ClassPathScanningCandidateComponentProvider scanner = this.getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(VertxDao.class);
        scanner.addIncludeFilter(annotationTypeFilter);

        // 使用spring boot默认的基础扫描包路径
        List<String> basePackages= AutoConfigurationPackages.get(this.beanFactory);
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata metadata = beanDefinition.getMetadata();
                    Assert.isTrue(metadata.isInterface(), "@VertxDao can only be specified on an interface");
                    registerVertxDao(beanDefinitionRegistry, metadata);
                }
            }
        }
    }

    private void registerVertxDao(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
                .genericBeanDefinition(VertxMySqlDaoFactoryBean.class);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        definition.addPropertyValue("type", className);
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        beanDefinition.setPrimary(true);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                if (!beanDefinition.getMetadata().isIndependent()) {
                    return false;
                } else {
                    if (beanDefinition.getMetadata().isInterface() &&
                            beanDefinition.getMetadata().getInterfaceNames().length == 1 &&
                            Annotation.class.getName().equals(beanDefinition.getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(), VertxMapperScannerRegistrar.this.classLoader);
                            return !target.isAnnotation();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            }
        };
    }
}
