/**
 * 字典管理初始化
 */
var Dict = {
    id: "DictTable",	//数据字典表格id
    idType: "DictTableType",	//类别id
    seItem: null,		//选中的条目
    seItemtype: null,		//选中的条目
    table: null,
    layerIndex: -1,
    tableType:null
};

/**
 * 初始化数据字典类型
 * 表格的列
 */
Dict.initColumntype = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '类别名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '类别编号', field: 'code', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'tips', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 初始化数据字典类型
 * 表格的列
 */
Dict.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: 'pid', field: 'pid', visible: false, align: 'center', valign: 'middle'},
        {title: '类型名称', field: 'nametype', align: 'center', valign: 'middle', sortable: true},
        {title: '类型编号', field: 'codetype', align: 'center', valign: 'middle', sortable: true},
        {title: '字典名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '字典编号', field: 'code', align: 'center', valign: 'middle', sortable: true},
        {title: '序号', field: 'num', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'tips', align: 'center', valign: 'middle', sortable: true}];
};


/**
 * 检查是否选中
 */
Dict.checktype = function () {
    var selected = $('#' + this.idType).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Dict.seItemtype = selected[0];
        return true;
    }
};
/**
 * 检查是否选中
 */
Dict.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Dict.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加字典类型
 */
Dict.openAddDict = function () {
    var index = layer.open({
        type: 2,
        title: '添加字典',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dict/dict_add'
    });
    this.layerIndex = index;
};

Dict.openDictDetails = function(){
    if (this.checktype()) {
        var index = layer.open({
            type: 2,
            title: '字典详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dict/dict_detail/'+ Dict.seItemtype.id
        });
        this.layerIndex = index;
    }
};


Dict.openDictDetailsDict = function(){
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '字典详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dict/dict_detail/'+ Dict.seItem.pid
        });
        this.layerIndex = index;
    }
};

/**
 * 点击添加字典，方法名字弄反了，就这样吧
 */
Dict.openAddDictType = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '添加字典',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dict/dict_add_dict/'+ Dict.seItem.pid
        });
        this.layerIndex = index;
    }
};

/**
 * 修改字典详情
 */
Dict.openDictDetail = function () {
    if (this.checktype()) {
        var index = layer.open({
            type: 2,
            title: '字典详情',
            area: ['900px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dict/dict_edit/' + Dict.seItemtype.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除字典类别
 */
Dict.delete = function () {
    if (this.checktype()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dict/delete", function (data) {
                Feng.success("删除成功!");
                Dict.tableType.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dictId", Dict.seItemtype.id);
            ajax.start();
        };

        Feng.confirm("是否刪除字典 " + Dict.seItemtype.name + "?", operation);
    }
};

/**
 * 删除字典
 */
Dict.deletedic = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dict/delete", function (data) {
                Feng.success("删除成功!");
                Dict.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dictId", Dict.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除字典 " + Dict.seItem.name +"【"+Dict.seItem.code+"】"+ "?", operation);
    }
};


/**
 * 查询字典列表
 */
Dict.searchType = function () {
    var queryData = {};
    queryData['nameType'] = $("#nameType").val();
    queryData['codeType'] = $("#codeType").val();
    queryData['tipsTpye'] = $("#tipsTpye").val();
    Dict.tableType.refresh({query: queryData});
};

/**
 * 重置form
 */
Dict.resetForm = function () {
    $("#home input").val("");
};
Dict.resetForm1 = function () {
    $("#ios input").val("");
};
/**
 * 查询字典列表
 */
Dict.search = function () {
    var queryData = {};
    queryData['name_dic'] = $("#name_dic").val();
    queryData['code_dic'] = $("#code_dic").val();
    queryData['pid_dic'] = $("#pid_dic").val();
    queryData['tips_dic'] = $("#tips_dic").val();
    Dict.table.refresh({query: queryData});
};

$(function () {
    init1();
    init2();
});
function init1(){
    var defaultColunms = Dict.initColumn();
    var table = new BSTable(Dict.id, "/dict/listDict", defaultColunms);
    table.setPaginationType("client");
    Dict.table = table.init();
}

function init2(){
    var defaultColunms = Dict.initColumntype();
    var tableType = new BSTable(Dict.idType, "/dict/listType", defaultColunms);
    tableType.setPaginationType("client");
    Dict.tableType = tableType.init();
}
//tab页面切换 页面刷新效果
$('ul.nav-tabs>li>a').on('click',function() {
    var $this = $(this);
    var divId = $this.attr("href");
    if(divId.indexOf("#ios")!=-1){
        $(".btn-outline").click();
    }
});
