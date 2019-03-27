/**
 * 公共参数名称管理初始化
 */
var Prop = {
    id: "PropTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Prop.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '版本', field: 'version', visible: false, align: 'center', valign: 'middle'},
            {title: 'APP_ID', field: 'appId', visible: false, align: 'center', valign: 'middle'},
            {title: '参数名称', field: 'propName', visible: true, align: 'center', valign: 'middle'},
            {title: '参数说明', field: 'propDesc', visible: true, align: 'center', valign: 'middle'},
            {title: '参数值', field: 'propValue', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Prop.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Prop.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加公共参数名称
 */
Prop.openAddProp = function () {
    var index = layer.open({
        type: 2,
        title: '添加公共参数名称',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/prop/prop_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看公共参数名称详情
 */
Prop.openPropDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '公共参数名称详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/prop/prop_update/' + Prop.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除公共参数名称
 */
Prop.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/prop/delete", function (data) {
            Feng.success("删除成功!");
            Prop.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("propId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询公共参数名称列表
 */
Prop.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Prop.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Prop.initColumn();
    var table = new BSTable(Prop.id, "/prop/list", defaultColunms);
    table.setPaginationType("client");
    Prop.table = table.init();
});
