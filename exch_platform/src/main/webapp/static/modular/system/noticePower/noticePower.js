/**
 * 通知管理管理初始化
 */
var NoticePower = {
    roleid: "roleid",	//表格id
    userid: "userid",
    seItem: null,		//选中的条目
    roleTable: null,
    userTable: null,
    layerIndex: -1,
    selecRoleid:null
};

/**
 * 初始化表格的列
 */
NoticePower.roleInitColumn = function () {
    var columns = [
        {field: 'selectItem', checkbox: true, events: roleDataEvent,formatter: NoticePower.roleIsCheck},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '上级角色', field: 'pName', align: 'center', valign: 'middle', sortable: true},
        {title: '所在部门', field: 'deptName', align: 'center', valign: 'middle', sortable: true},
        {title: '别名', field: 'tips', align: 'center', valign: 'middle', sortable: true}]
    return columns;
};

window.roleDataEvent ={
    'click .bs-checkbox ': function(e,value,row,index){
        alert(1);
    }
};

NoticePower.userInitColumn = function () {
    var columns = [
        {field: 'selectItem', checkbox: true,formatter: NoticePower.userIsCheck},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '部门', field: 'deptName', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true}];
    return columns;
};

NoticePower.roleIsCheck=function (value, row, index) {
    if (row.state == true)
        return {
            checked : true//设置选中
        };
    return value;
};
NoticePower.userIsCheck=function (value, row, index) {
    if (row.state == true)
        return {
            checked : true//设置选中
        };
    return value;
};




/**
 * 检查是否选中
 */
NoticePower.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        NoticePower.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知管理
 */
NoticePower.openAddNoticePower = function () {
    var index = layer.open({
        type: 2,
        title: '添加通知管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/noticePower/noticePower_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知管理详情
 */
NoticePower.openNoticePowerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '通知管理详情',
            area: ['900px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/noticePower/noticePower_update/' + NoticePower.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知管理
 */
NoticePower.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/noticePower/delete", function (data) {
            Feng.success("删除成功!");
            NoticePower.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("noticePowerId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询通知管理列表
 */
NoticePower.searchRole = function () {
    var queryData = {};
    queryData['rolename'] = $("#roleName").val();
    NoticePower.roleTable.refresh({query: queryData});
};
NoticePower.searchUser = function () {
    var queryData = {};
    queryData['username'] = $("#userName").val();
    NoticePower.userTable.refresh({query: queryData});
};



NoticePower.roleClick= function (a){
    var queryData = {};
    queryData['noticeId'] = window.parent.Notice.seleid;
    queryData['roleId'] = a.id;
    NoticePower.userTable.refresh({query: queryData});
    $("#userh").html("角色： "+a.name);
    NoticePower.selecRoleid=a.id;
};

/**
 * 保存数据
 */
NoticePower.updateNoticePower = function (){
    var userIds="";
    var selected = $('#' + NoticePower.userid).bootstrapTable('getSelections');
    if(selected.length == 0){
    }else{
        for(var i in selected){
            userIds+=selected[i].id+","
        }
    }


    var ajax = new $ax(Feng.ctxPath + "/noticePower/updateNoticePower", function (data) {
        Feng.success("保存成功!");
        NoticePower.roleTable.refresh();
        NoticePower.userTable.refresh();
    }, function (data) {
        Feng.error("保存失败!" + data.responseJSON.message + "!");
    });
    ajax.set("noticeId",window.parent.Notice.seleid);
    ajax.set("roleId",NoticePower.selecRoleid);
    ajax.set("userIds",userIds);
    ajax.start();

};

$(function () {
    var queryData = {};
    queryData['noticeId'] = window.parent.Notice.seleid;

    var role = NoticePower.roleInitColumn();
    var roletable = new BSTable(NoticePower.roleid, "/noticePower/rolelist",role, NoticePower.roleClick);
    roletable.setPaginationType("client");
    roletable.setQueryParams(queryData);
    roletable.setClickToSelect(false);
    NoticePower.roleTable = roletable.init();

    var user = NoticePower.userInitColumn();
    var userTable = new BSTable(NoticePower.userid, "/noticePower/userlist",user);
    userTable.setPaginationType("client");
    NoticePower.userTable = userTable.init();
});
