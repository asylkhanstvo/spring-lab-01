package kz.iitu.springlab.scope;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class ScopeDemoService {

    private final SingletonBean singletonBean;
    private final PrototypeBean prototypeBean;
    private final ObjectProvider<PrototypeBean> prototypeProvider;

    public ScopeDemoService(
            SingletonBean singletonBean,
            PrototypeBean prototypeBean,
            ObjectProvider<PrototypeBean> prototypeProvider) {

        this.singletonBean = singletonBean;
        this.prototypeBean = prototypeBean;
        this.prototypeProvider = prototypeProvider;
    }

    public String singletonId() {
        return singletonBean.getId();
    }

    public String prototypeInjectedId() {
        return prototypeBean.getId();
    }

    public String prototypeFromProviderId() {
        return prototypeProvider.getObject().getId();
    }
}