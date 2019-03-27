package com.exch.platform.flowable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 工作流的业务测试
 *
 * 先发布-启动
 */
public class FlowableTest {

    ProcessEngine processEngine;

    @Before
    public void init() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable?autoReconnect=true&useUnicode=true&characterEncoding=utf8")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

//        cfg.buildProcessEngine();
        processEngine = cfg.buildProcessEngine();
//        cfg.createDbSqlSessionFactory();
    }

    /**
     * 发布流程
     */
    @Test
    public void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("MyExpenseProcess.bpmn20.xml").key("testKEY").name("第一次").category("重要")
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                    .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }

    /**
     * 查看发布历史
     *
     *
     */
    @Test
    public void findDeploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : list) {
            System.out.println(deployment.getEngineVersion());
            System.out.println(deployment.getCategory());
            System.out.println(deployment.getName());
            System.out.println("deploy = " + deployment.getId());
            System.out.println("key = " + deployment.getKey());
            System.out.println("------");
        }
    }
    /**
     * 查看流程
     */
    @Test
    public void findProcessDefinition(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> processDefinition = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        processDefinition.forEach((obj)->{
            System.out.println(obj);
            System.out.println("Key"+obj.getKey());
            System.out.println("Version"+obj.getVersion());
        });

    }

    /**
     * 查看流程定义
     */
    @Test
    public void queryProcess() {
        //启动的流程
        List<ProcessInstance> list1 = processEngine.getRuntimeService().createProcessInstanceQuery().list();
        for (ProcessInstance processDefinition : list1) {
            //proc_inst_id
            System.out.println("id = " + processDefinition.getId());
//            System.out.println(processDefinition.getProcessVariables());
            //deploymentid模板
            System.out.println("getDeploymentId = " + processDefinition.getDeploymentId());
            System.out.println("getTenantId = " + processDefinition.getTenantId());
            System.out.println("name = " + processDefinition.getName());
        }

        //发布的流程
        System.err.println("---------------------------------");
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println("id = " + processDefinition.getId());
            System.out.println("getDeploymentId = " + processDefinition.getDeploymentId());
            System.out.println("getTenantId = " + processDefinition.getTenantId());
            System.out.println("name = " + processDefinition.getName());
            System.out.println("key = " + processDefinition.getKey());
            System.out.println("version = " + processDefinition.getVersion());
            System.out.println("resourceName = " + processDefinition.getResourceName());
        }
    }


    /**
     * 启动流程
     */
    @Test
    public void start() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskUser", "fsn");
        map.put("money", "600");
        map.put("pro1", "参数1");
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("Expense", map);
        System.out.println("pid = " + processInstance.getId());
        System.out.println("activityId = " + processInstance.getActivityId());
        System.out.println("getProcessDefinitionId = " + processInstance.getProcessDefinitionId());
    }
    /**
     * 删除发布 ProcessDefinition
     */
    @Test
    public void delProcessDefinition(){
        
    }

    /**
     * 删除流程定义(删除任务)ProcessInstance
     */
    @Test
    public void delProcess() {
        processEngine.getRuntimeService().deleteProcessInstance("5001","abcd");
        //processEngine.getRepositoryService().deleteDeployment("25001", true);
        System.out.println("删除成功");
    }

    /**
     * 查看任务
     */
    @Test
    public void queryMyTask() {
        String name = "";
//taskAssignee(name)
        TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
        List<Task> list = taskQuery.list();
        for (Task task : list) {
            System.out.println("id = " + task.getId());
            System.out.println("name = " + task.getName());//目前节点
            System.out.println("Assignee = " + task.getAssignee());//目前节点
            System.out.println(JSON.toJSON(task.getTaskLocalVariables()));
        }

        System.out.println("查询完毕!");
    }

    /**
     * 完成任务
     */
    @Test
    public void complete() {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("outcome", "通过");
//        map.put("outcome", "同意");
        map.put("money", 100);
        TaskService taskService = processEngine.getTaskService();
        taskService.complete("5008",map);
    }

    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://127.0.0.1:3306/flowable?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());


        Scanner scanner = new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("holidayRequest", variables);


        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }

        System.out.println("Which task would you like to complete?");
        int taskIndex = Integer.valueOf(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " wants " +
                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");

        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        variables = new HashMap<String, Object>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);
    }

}
