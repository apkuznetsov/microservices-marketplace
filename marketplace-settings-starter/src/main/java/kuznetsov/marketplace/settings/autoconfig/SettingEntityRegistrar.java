package kuznetsov.marketplace.settings.autoconfig;

import kuznetsov.marketplace.settings.domain.SettingStarter;
import kuznetsov.marketplace.settings.repo.SettingRepoStarter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class SettingEntityRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AutoConfigurationPackages.register(
                registry,
                SettingRepoStarter.class.getPackageName(),
                SettingStarter.class.getPackageName()
        );
    }

}
