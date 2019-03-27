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
        {title: '查询名称', field: 'methodName', visible: true, align: 'center', valign: 'middle'},
        {title: '查询Mapper', field: 'method', visible: true, align: 'center', valign: 'middle'},
        {title: 'SQL描述', field: 'sqllist',width:"auto",visible: true, align: 'center',valign: 'middle',formatter:DataMethodFilter.mformatter,}
    ];
};

DataMethodFilter.mformatter=function(data){
    var selehtml="<select onchange='DataMethodFilter.checkField("+Role.roleId+",this.value,"+data[0]["methodId"]+")'><option value =''>无</option>";
    if(data.length!=0){
        for(var i in data){
            var selectone="";
            if(data[i]["select"]!=undefined){
                selectone="selected = 'selected'"
            }

            selehtml+="<option value ='"+data[i]["id"]+"' "+selectone+">"+data[i]["depict"]+"</option>";
        }
        selehtml+="</select>";
        return selehtml;
    }
};

DataMethodFilter.checkField=function (roleId,sqlId,methodId){
    console.log(roleId,sqlId,methodId);
    var queryData = {};
    queryData['roleId'] = roleId;
    queryData['sqlId'] = sqlId;
    queryData['methodId'] = methodId;
    var ajax = new $ax(Feng.ctxPath + "/dataSqlFilter/updateRoleSQL", function (data) {
        Feng.success("修改成功!");
        var queryData = {};
        queryData['roleId'] = Role.roleId;
        DataMethodFilter.table.refresh({query: queryData});
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.setData(queryData);
    ajax.start();
};


$(function () {
    var defaultColunms = DataMethodFilter.initColumn();
    var table = new BSTable(DataMethodFilter.id, "/dataMethodFilter/methodAndSqllist", defaultColunms );
    table.setPaginationType("client");
    DataMethodFilter.table = table.init();
});
