/**
 * 初始化拦截数据SQL详情对话框
 */
var DataSqlFilterInfoDlg = {
    dataSqlFilterInfoData : {}
};

/**
 * 清除数据
 */
DataSqlFilterInfoDlg.clearData = function() {
    this.dataSqlFilterInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataSqlFilterInfoDlg.set = function(key, val) {
    this.dataSqlFilterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataSqlFilterInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DataSqlFilterInfoDlg.close = function() {
    parent.layer.close(window.parent.DataSqlFilter.layerIndex);
}

/**
 * 收集数据
 */
DataSqlFilterInfoDlg.collectData = function() {
    this
    .set('id')
    .set('roleId')
    .set('methodId')

    .set('filterSql')
    .set('depict');
}

/**
 * 提交添加
 */
DataSqlFilterInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dataSqlFilter/add", function(data){
        Feng.success("添加成功!");
        var queryData = {};
        queryData['methodId'] = window.parent.DataSqlFilter.methodId;

        window.parent.DataSqlFilter.table.refresh({query: queryData});
        DataSqlFilterInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataSqlFilterInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DataSqlFilterInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dataSqlFilter/update", function(data){
        Feng.success("修改成功!");
        var queryData = {};
        queryData['methodId'] = window.parent.DataSqlFilter.methodId;

        window.parent.DataSqlFilter.table.refresh({query: queryData});
        DataSqlFilterInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataSqlFilterInfoData);
    ajax.start();
}

$(function() {

});
