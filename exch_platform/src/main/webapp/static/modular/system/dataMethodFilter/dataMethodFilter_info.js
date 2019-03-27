/**
 * 初始化l拦截数据方法详情对话框
 */
var DataMethodFilterInfoDlg = {
    dataMethodFilterInfoData : {}
};

/**
 * 清除数据
 */
DataMethodFilterInfoDlg.clearData = function() {
    this.dataMethodFilterInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataMethodFilterInfoDlg.set = function(key, val) {
    this.dataMethodFilterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataMethodFilterInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DataMethodFilterInfoDlg.close = function() {
    parent.layer.close(window.parent.DataMethodFilter.layerIndex);
}

/**
 * 收集数据
 */
DataMethodFilterInfoDlg.collectData = function() {
    this.set('id')
    .set('method')
    .set('methodName')
}

/**
 * 提交添加
 */
DataMethodFilterInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dataMethodFilter/add", function(data){
        Feng.success("添加成功!");
        window.parent.DataMethodFilter.table.refresh();
        DataMethodFilterInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataMethodFilterInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DataMethodFilterInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dataMethodFilter/update", function(data){
        Feng.success("修改成功!");
        window.parent.DataMethodFilter.table.refresh();
        DataMethodFilterInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataMethodFilterInfoData);
    ajax.start();
}

$(function() {

});
