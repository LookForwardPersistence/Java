### MapStruct
- 引入 jar包
~~~
  <!-- 实体转换器 -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-jdk8</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <scope>provided</scope>
        </dependency>
~~~
- 示例
~~~
@Mapper(componentModel = "spring")
public interface MessageLogMapper {

    @Mappings({
            @Mapping(target = "accounts",expression = "java(org.apache.commons.lang3.StringUtils.join(messageCommand.getAccounts(),\",\"))"),
            @Mapping(target = "codes",expression = "java(org.apache.commons.lang3.StringUtils.join(messageCommand.getCodes(),\",\"))")
    })
    MessageLog toMessageLog(MessageCommand messageCommand);
}
~~~
