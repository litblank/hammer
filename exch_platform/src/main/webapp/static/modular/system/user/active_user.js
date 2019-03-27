/**
 * 系统管理--用户管理的单例对象
 */
var ActiveUser = {
    id: "activeUser",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
ActiveUser.initColumn = function () {
    var columns = [
        {title: '用户名', field: 'user', align: 'center', valign: 'middle'},
        {title: 'ip', field: 'ip', align: 'center', valign: 'middle', sortable: true},
        {title: '最后操作时间', field: 'operation', align: 'center', valign: 'middle', sortable: true}]
    return columns;
};

ActiveUser.onClickDept = function (e, treeId, treeNode) {
    MgrUser.deptid = treeNode.id;
    MgrUser.search();
};

$(function () {
    var defaultColunms = ActiveUser.initColumn();
    var table = new BSTable(ActiveUser.id, "/usersession/getActiveUser", defaultColunms);
    table.setPaginationType("client");
    ActiveUser.table = table.init();
});
