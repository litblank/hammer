/**
 * l拦截数据方法管理初始化
 */
var DataMethodFilter = {
    id: "DataMethodFilterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DataMethodFilter.initColumn = function () {
    return [
        {field: 'selectItem1', radio: true},
            // {title: '主键ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '查询名称', field: 'methodName', visible: true, align: 'center', valign: 'middle'},
            {title: '查询Mapper', field: 'method', visible: true, align: 'center', valign: 'middle'},
            // {title: '创建人ID', field: 'createrId', visible: true, align: 'center', valign: 'middle'},
            // {title: '创建时间', field: 'createrDate', visible: true, align: 'center', valign: 'middle'},
            // {title: '修改人ID', field: 'modifyId', visible: true, align: 'center', valign: 'middle'},
            // {title: '修改时间', field: 'modifyDate', visible: true, align: 'center', valign: 'middle'},
            // {title: '是否删除（0否，1是）', field: 'isDelete', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
DataMethodFilter.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DataMethodFilter.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加l拦截数据方法
 */
DataMethodFilter.openAddDataMethodFilter = function () {
    var index = layer.open({
        type: 2,
        title: '添加l拦截数据方法',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dataMethodFilter/dataMethodFilter_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看l拦截数据方法详情
 */
DataMethodFilter.openDataMethodFilterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'l拦截数据方法详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dataMethodFilter/dataMethodFilter_update/' + DataMethodFilter.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除l拦截数据方法
 */
DataMethodFilter.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/dataMethodFilter/delete", function (data) {
            Feng.success("删除成功!");
            DataMethodFilter.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("dataMethodFilterId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询l拦截数据方法列表
 */
DataMethodFilter.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    DataMethodFilter.table.refresh({query: queryData});
};

DataMethodFilter.monclick = function(row, $element,field){
    if(row.id!=null){
        var queryData = {};
        queryData['methodId'] = row.id;
        var ajax = new $ax(Feng.ctxPath + "/dataSqlFilter/list", function (data) {
            if(data.length!=0){
                $("#"+DataSqlFilter.id).bootstrapTable('load', data);
                DataSqlFilter.roleId=data[0].roleId
                DataSqlFilter.methodId=data[0].methodId
                $("#button_1").show();
                $("#button_2").show();
                $("#button_3").show();
            }else{
                $("#"+DataSqlFilter.id).bootstrapTable('removeAll');
                DataSqlFilter.methodId=row.id;
                $("#button_1").show();
                $("#button_2").show();
                $("#button_3").show();
            }
        }, function (data) {

        });
        ajax.setData(queryData);
        ajax.start();

    }
};

$(function () {
    $("#"+DataMethodFilter.id).attr("data-select-item-name","DataMethod");
    var defaultColunms = DataMethodFilter.initColumn();
    var table = new BSTable(DataMethodFilter.id, "/dataMethodFilter/list", defaultColunms,DataMethodFilter.monclick );
    table.setPaginationType("client");
    DataMethodFilter.table = table.init();
});
