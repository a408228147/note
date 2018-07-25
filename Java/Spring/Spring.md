### 问题一、了解下BeanFactory和FactoryBean的区别？
     * BeanFactory是个Factory，也就是 IOC 容器或对象工厂，所有的 Bean 都是由 BeanFactory( 也就是 IOC 容器 ) 来进行管理。 
     * FactoryBean是一个接口，是一个能生产或者修饰生成对象的工厂Bean(本质上也是一个bean)，可以在BeanFactory（IOC容器）中被管理，所以它并不是一个简单的Bean。当使用容器中factory bean的时候，该容器不会返回factory bean本身，而是返回其生成的对象。要想获取FactoryBean的实现类本身，得在getBean(String BeanName)中的BeanName之前加上&,写成getBean(String &BeanName)。

### 问题二、BeanPostProcessor和BeanFactoryPostProcessor的区别？
     * BeanFactoryPostProcessor和BeanPostProcessor都是Spring初始化bean的扩展点。两个接口非常相似。
     *  BeanFactoryPostProcessor可以对bean的定义（配置元数据）进行处理。Spring IoC容器允许BeanFactoryPostProcessor在容器实际实例化任何其它的bean之前读取配置元数据，并有可能修改它。可以配置多个BeanFactoryPostProcessor。还能通过设置'order'属性来控制BeanFactoryPostProcessor的执行次序。
     * BeanFactoryPostProcessor不允许对bean进行修改。

### 问题三、Spring bean的几种初始化方式，先后顺序是什么？
#### 初始化方式
    * 通过实现InitializingBean/DisposableBean 接口来定制初始化之后/销毁之前的操作方法；
    * 通过<bean> 元素的 init-method/destroy-method属性指定初始化之后 /销毁之前调用的操作方法；
    * 在指定方法上加上@PostConstruct或@PreDestroy注解来制定该方法是在初始化之后还是销毁之前调用。
#### 先后顺序
    * Bean在实例化的过程中：Constructor > @PostConstruct >InitializingBean > init-method
    * Bean在销毁的过程中：@PreDestroy > DisposableBean > destroy-method
    
