package cn.iheng.springboot.starter;

import cn.iheng.springboot.starter.annotation.VertxDao;
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

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author iheng
 * @date 1/26/18
 */
public class VertxDaoRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;
    private ClassLoader classLoader;
    private Environment environment;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        ClassPathScanningCandidateComponentProvider scanner = this.getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(VertxDao.class);
        scanner.addIncludeFilter(annotationTypeFilter);
        Set<String> basePackages = this.getBasePackages(annotationMetadata);

        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                if (candidateComponent instanceof AnnotatedBeanDefinition) {
                    AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                    AnnotationMetadata metadata=beanDefinition.getMetadata();
                    Assert.isTrue(metadata.isInterface(),"@VertxDao can only be specified on an interface");
                    Map<String,Object> attrs=metadata.getAnnotationAttributes(VertxDao.class.getCanonicalName());
                    registerVertxDao(beanDefinitionRegistry,metadata,attrs);
                }
            }
        }
    }

    private void registerVertxDao(BeanDefinitionRegistry registry,AnnotationMetadata annotationMetadata,Map<String,Object> attributes){
        String className=annotationMetadata.getClassName();
        BeanDefinitionBuilder definition=BeanDefinitionBuilder
                .genericBeanDefinition(VertxMySqlDaoFactoryBean.class);
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        AbstractBeanDefinition beanDefinition=definition.getBeanDefinition();
        beanDefinition.setPrimary(true);
        BeanDefinitionHolder holder=new BeanDefinitionHolder(beanDefinition,className);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder,registry);
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
                            Class<?> target = ClassUtils.forName(beanDefinition.getMetadata().getClassName(), VertxDaoRegistrar.this.classLoader);
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

    /**
     * 暂时默认类路径
     *
     * @param metadata
     * @return
     */
    protected Set<String> getBasePackages(AnnotationMetadata metadata) {
        Set<String> basePackages = new HashSet<>();
        basePackages.add(ClassUtils.getPackageName(metadata.getClassName()));
        return basePackages;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
