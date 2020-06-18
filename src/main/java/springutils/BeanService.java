package springutils;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BeanService {

    private ApplicationContext context;

    @Autowired
    public BeanService(ApplicationContext context) {
        this.context = context;
    }

    public <T> T getBeanByName(String name) throws BeanCreationException {
        boolean containsBean = context.containsBean(name);

        if (containsBean) {
            return (T) context.getBean(name);
        }

        throw new BeanCreationException(name);
    }

    public <T> T getBeanByClass(Class<T> type) throws BeanCreationException {
        boolean annotationPresent = type.isAnnotationPresent(Component.class);

        if (annotationPresent) {
            return (T) context.getBean(type);
        }

        throw new BeanCreationException(type.getSimpleName());
    }
}
