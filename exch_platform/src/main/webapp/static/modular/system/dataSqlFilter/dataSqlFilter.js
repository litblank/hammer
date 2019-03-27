/**
 * 拦截数据SQL管理初始化
 */
var DataSqlFilter = {
    id: "DataSqlFilterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    roleId:null,
    methodId:null
};

/**
 * 初始化表格的列
 */
DataSqlFilter.initColumn = function () {
    return [
        {field: 'selectItem',radio: true},
            // {title: '主键ID', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '角色ID', field: 'roleId', visible: false, align: 'center', valign: 'middle'},
            {title: '方法ID', field: 'methodId', visible: false, align: 'center', valign: 'middle'},
            {title: '过滤SQL', field: 'filterSql', visible: true, align: 'center', valign: 'middle'},
            {title: 'SQL描述', field: 'depict', visible: true, align: 'center', valign: 'middle'},
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
DataSqlFilter.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DataSqlFilter.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加拦截数据SQL
 */
DataSqlFilter.openAddDataSqlFilter = function () {
    var index = layer.open({
        type: 2,
        title: '添加拦截数据SQL',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dataSqlFilter/dataSqlFilter_add?roleId='+DataSqlFilter.roleId+"&methodId="+DataSqlFilter.methodId
    });
    this.layerIndex = index;
};

/**
 * 打开查看拦截数据SQL详情
 */
DataSqlFilter.openDataSqlFilterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '拦截数据SQL详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dataSqlFilter/dataSqlFilter_update/' + DataSqlFilter.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除拦截数据SQL
 */
DataSqlFilter.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/dataSqlFilter/delete", function (data) {
            Feng.success("删除成功!");
            DataSqlFilter.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("dataSqlFilterId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询拦截数据SQL列表
 */
DataSqlFilter.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    DataSqlFilter.table.refresh({query: queryData});
};


$(function () {
    $("#button_1").hide();
    $("#button_2").hide();
    $("#button_3").hide();
    var defaultColunms = DataSqlFilter.initColumn();
    var table = new BSTable(DataSqlFilter.id, "/dataSqlFilter/list", defaultColunms,DataSqlFilter.monclick);
    table.setPaginationType("client");
    DataSqlFilter.table = table.init();
});
