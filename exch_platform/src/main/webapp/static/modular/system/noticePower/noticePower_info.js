/**
 * 初始化通知管理详情对话框
 */
var NoticePowerInfoDlg = {
    noticePowerInfoData : {}
};

/**
 * 清除数据
 */
NoticePowerInfoDlg.clearData = function() {
    this.noticePowerInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticePowerInfoDlg.set = function(key, val) {
    this.noticePowerInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
NoticePowerInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
NoticePowerInfoDlg.close = function() {
    parent.layer.close(window.parent.NoticePower.layerIndex);
}

/**
 * 收集数据
 */
NoticePowerInfoDlg.collectData = function() {
    this
    .set('id')
    .set('noticeId')
    .set('roleId')
    .set('userId');
}

/**
 * 提交添加
 */
NoticePowerInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/noticePower/add", function(data){
        Feng.success("添加成功!");
        window.parent.NoticePower.table.refresh();
        NoticePowerInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticePowerInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
NoticePowerInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/noticePower/update", function(data){
        Feng.success("修改成功!");
        window.parent.NoticePower.table.refresh();
        NoticePowerInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.noticePowerInfoData);
    ajax.start();
}

$(function() {

});
