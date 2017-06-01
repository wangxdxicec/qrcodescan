<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>金泓信展览扫码平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <!--<script type="text/javascript" src="${base}/resource/js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="${base}/resource/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${base}/resource/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/resource/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${base}/resource/easyui/empty-view.js"></script>
    <script type="text/javascript" src="${base}/resource/common.js"></script>
    <script type="text/javascript" src="${base}/resource/easyui/easyui-validate.js"></script>-->

    <link type="text/css" href="${base}/resource/css/style.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${base}/resource/easyui/themes/metro-blue/easyui.css">
    <link rel="stylesheet" type="text/css" href="${base}/resource/easyui/themes/icon.css">
    <script type="text/javascript" src="${base}/resource/js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="${base}/resource/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${base}/resource/jquery.min1.js"></script>
    <script type="text/javascript" src="${base}/resource/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${base}/resource/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${base}/resource/common.js"></script>
    <script type="text/javascript" src="${base}/resource/easyui/easyui-validate.js"></script>

    <style>
        *{padding:0;margin:0;}

        #dialogVisitorPass .overlayCon
        {
            text-align:center;
        }
        .credentialsPreview .prevHeader
        {
            height:80px;
            /*background:url(/images/logo_stonefair_reg.jpg) 15px center no-repeat;*/
            /*padding-left:115px;*/
            font-size:16px;
            font-weight:bold;
            line-height:80px;
            cursor:default;
        }
        .credentialsPreview h4 {
            padding: 3px 0;
            margin: 0;
            text-align: center;
            font-weight: normal;
            font-size: 22px;
            line-height: 40px;
            width: 100%;
        }
        .credentialsPreview h5 {
            padding: 0;
            margin: 0;
            text-align: center;
            font-weight: normal;
            font-size: 18px;
            line-height: 150%;
            width: 100%;
        }
        .checkOuter{
            overflow: hidden;
            width:450px;
            margin:0 auto;

        }
        .checkbox{
            float:left;
            line-height:20px;
            padding-left:30px;
            box-sizing: border-box;
        }
        .checkbox>label{
            font-size: 18px;
            font-family: "微软雅黑";
        }
        .checkboxInner{
            float:left;
        }
        .checkInput{
            margin-left:30px;
            margin-right:10px;
        }
    </style>
</head>


<body style="width:100%;">
<div id="wholebg" style="position: fixed;top:0;z-index: -1000;"><img alt="" src="${base}/resource/scancode.jpg" style="width: 100%;"></div>
<!--<div class="links" >
    <a href="#" id="queryCheckingNo" style="font-family: Arial; border: 0;"><font color="#dc143c">搜索登记号</font></a>
</div>-->
<div id="leftview" name="leftview" style="float: left; width: 65%; height: 70%; margin-top: 150px; margin-right: 20px;">
    <div class="checkOuter">
        <div class="checkbox" id="faircheckbox" name="faircheckbox">
            <!--<input type="checkbox" id="buddhafair" class="checkboxInner checkInput" checked="checked">
            <label class="checkboxInner">佛事展</label>

            <input type="checkbox" id="gmf" class="checkboxInner checkInput">
            <label class="checkboxInner">矿物展</label>

            <input type="checkbox" id="health" class="checkboxInner checkInput">
            <label class="checkboxInner">健康展</label>-->
        </div>
    </div>
    <div style="height: 420px;" class="easyui-panel" title="客商列表">
        <table id="userList" style="width: 100%; height:370px;border: 1px solid #151515;margin-left: 30px;"
               data-options="url:'${base}/user/queryCustomerListByFairIdAndPage?type=1',
                   loadMsg: '数据加载中......',
                   singleSelect:false,	//只能当行选择：关闭
                   fit:true,
                   fitColumns:true,
                   idField:'id',
                   remoteSort:true,
                   emptyMsg: '没有记录',
                   rownumbers: 'true',
                   pagination:'true',
                   pageSize:'20'">
            <thead>
            <tr>
                <th data-options="field: 'checkingno', width: $(this).width() / 10">
                    登记号<br/>
                    <input id="customerCheckingno" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                </th>
                <th data-options="field: 'firstName', width: $(this).width() / 10">
                    姓名<br/>
                    <input id="customerFirstName" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                </th>
                <th data-options="field: 'company', width: $(this).width() / 7">
                    公司<br/>
                    <input id="customerCompany" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                </th>
                <th data-options="field: 'email', width: $(this).width() / 10">
                    邮箱<br/>
                    <input id="customerEmail" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                </th>
                <th data-options="field: 'mobilePhone', width: $(this).width() / 10">
                    手机<br/>
                    <input id="customerMobilePhone" style="width:100%;height:15px;" type="text" onkeyup="filter();"/>
                </th>
            </tr>


            </thead>
        </table>
    </div>
</div>

<div id="rightview" name="rightview" style="float: right; width: 25%; height: 70%; margin-top: 120px; margin-right: 30px;">
    <table style="width: 100%; height:420px; border: 1px solid #151515; padding-top:50px; padding-left:30px;background: url('${base}/resource/idcardinfobg.jpg');background-repeat: no-repeat">
        <tr>
            <td>姓名：</td>
            <td style="width: 80px; text-align: center" colspan="2">
                <label id="userName" type="text" name="userName" style="width: 155px" />
            </td>
        </tr>
        <tr>
            <td>公司：</td>
            <td style="width: 80px; text-align: center" colspan="2">
                <label id="userCompany" type="text" name="userCompany" style="width: 155px" />
            </td>
        </tr>
        <tr>
            <td>登记号：</td>
            <td style="width: 80px; text-align: center" colspan="2">
                <label id="checkingno" type="text" name="checkingno" style="width: 155px" />
            </td>
        </tr>
        <tr>
            <td>扫描次数：</td>
            <td style="width: 80px; text-align: center" colspan="4">
                <label id="scanNum" type="text" name="scanNum" style="width: 60%;" />
            </td>
        </tr>
        <tr>
            <td>会刊领取：</td>
            <td style="width: 80px; text-align: center" colspan="4">
                <label id="hastakeproceedStatus" type="text" name="hastakeproceedStatus" style="width: 155px;" />
            </td>
        </tr>
    </table>
</div>
<!--<div id="scancode" align="center" style="font-size: 32px; height: 140px;margin-top:40px;">
    登记号：<input type="text" style="width:50%;height:50%;font-size:42px;" name="scancodeText" id="scancodeText"
               oninput="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"/>
    <select id="col_name" name="col_name" style="font-size: 32px; padding-left:15px; margin-left: 20px;"></select>
    &lt;!&ndash;<label><input type="checkbox" id="scanbyphone" /><span style="font-size:24px;padding-left:15px;">手机号扫描</span></label>&ndash;&gt;
</div>
<div align="center" style="height: 140px;padding-left: 50px;">
    <button type="button" id="hastakeproceedBtn" name="hastakeproceedBtn" style="height: 80px;width: 220px;font-size:20px; display: none" onclick="setHastakeproceed()">我要领会刊</button>
    <button type="button" style="height: 80px;width: 220px;font-size:32px;" onclick="handScanCode()">扫描</button>
</div>
<div id="scancount" style="height: 200px;padding-top: 20px;margin-left: 90px;margin-top: 50px;">
    <label style="width:50%;height:50%;font-size:32px;color: red" name="scancountText" id="scancountText" />
</div>

<input type="hidden" id="takeproceedqrcodevalue" name="takeproceedqrcodevalue"/>-->

<!-- 搜索登记号表单 -->
<!--<div id="queryCheckingNoDlg" data-options="iconCls:'icon-add',modal:true">
    <form id="queryCheckingNoForm" name="queryCheckingNoForm">
        <table style="width: 320px;margin: 20px auto">
            <tr>
                <td style="width: 90px;text-align: right">手机号：</td>
                <td><input class="easyui-validatebox" style="width: 160px;height:30px;" type="text" name="telephone" id="telephone"></td>
            </tr>
        </table>
    </form>
</div>-->
<script type="text/javascript">
    var checkedUserInfo = [];
    function getFairIndex() {
        for(var i=0,a;a=fairInfoList[i++];){
            var checkBox = document.getElementById(a.fairalairname);
            if(checkBox != null && checkBox.checked){
                return a.fairid;
            }
        }
    }

    $(function() {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/user/loadAllEnableFairName",
            success: function (result) {
                if (result) {
                    fairInfoList = result;
                    var faircheckboxhtml = document.getElementById("faircheckbox");
                    faircheckboxhtml.innerHTML = "";
                    for (var i = 0, a; a = result[i++];) {
                        if (i == 1) {
                            var input = document.createElement("input");
                            input.setAttribute("type", "checkbox");
                            input.setAttribute("class", "checkboxInner checkInput");
                            input.setAttribute("id", a.fairalairname);
                            input.setAttribute("checked", "checked");
                            //input.setAttribute("click", "resetcheck()");
                            input.onclick = function () {
                                $(this).attr('checked', 'checked').siblings('input').removeAttr('checked');
                                var fairIndex = getFairIndex();
                                filter(fairIndex);
                            };
                            faircheckboxhtml.appendChild(input);
                            var label = document.createElement("label");
                            label.setAttribute("class", "checkboxInner");
                            label.innerHTML = a.fairname;
                            faircheckboxhtml.appendChild(label);
                        } else {
                            var input = document.createElement("input");
                            input.setAttribute("type", "checkbox");
                            input.setAttribute("class", "checkboxInner checkInput");
                            input.setAttribute("id", a.fairalairname);
                            input.onclick = function () {
                                $(this).attr('checked', 'checked').siblings('input').removeAttr('checked');
                                var fairIndex = getFairIndex();
                                filter(fairIndex);
                            };
                            faircheckboxhtml.appendChild(input);
                            var label = document.createElement("label");
                            label.setAttribute("class", "checkboxInner");
                            label.innerHTML = a.fairname;
                            faircheckboxhtml.appendChild(label);
                        }
                    }
                }
            }
        });

        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${base}/user/loadAllColFieldList",
            success: function (result) {
                if (result) {
                    $("#fair_name").html('');
                    for (var i = 0, a; a = result[i++];) {
                        $("#col_name").append('<option onclick="refreshCurrentInput()" value="' + a.colfield + '">' + a.colname + '</option>');
                    }
                    //$("#col_name").options[0].selected = true;
                }
            }
        });
    });

    function filter(fairIndex){
        var filterParm = "?type=" + fairIndex;
        if(document.getElementById("customerCheckingno").value != ""){
            filterParm += '&checkingno=' + encodeURI(document.getElementById("customerCheckingno").value);
        }
        if(document.getElementById("customerFirstName").value != ""){
            filterParm += '&firstName=' + encodeURI(document.getElementById("customerFirstName").value);
        }
        if(document.getElementById("customerCompany").value != ""){
            filterParm += '&company=' + encodeURI(document.getElementById("customerCompany").value);
        }
        if(document.getElementById("customerEmail").value != ""){
            filterParm += '&email=' + encodeURI(document.getElementById("customerEmail").value);
        }
        if(document.getElementById("customerMobilePhone").value != ""){
            filterParm += '&customerMobilePhone=' + encodeURI(document.getElementById("customerMobilePhone").value);
        }
        $('#userList').datagrid('options').url = '${base}/user/queryCustomerListByFairIdAndPage' + filterParm;
        $('#customers').datagrid('reload');
    }

    //客商列表渲染
    $("#userList").datagrid({
        onSelect:function (rowIndex, rowData){
            var row = $('#userList').datagrid('getSelections');
            for (var i = 0; i < row.length; i++) {
                if (findCheckedProduct(row[i].id) == -1) {
                    checkedUserInfo.push(row[i].id);
                }
            }
        },
        onUnselect:function (rowIndex, rowData){
            var k = findCheckedProduct(rowData.id);
            if (k != -1) {
                checkedUserInfo.splice(k, 1);
            }
        },
        onSelectAll:function (rows){
            for (var i = 0; i < rows.length; i++) {
                var k = findCheckedProduct(rows[i].id);
                if (k == -1) {
                    checkedUserInfo.push(rows[i].id);
                }
            }
        },
        onUnselectAll:function (rows){
            for (var i = 0; i < rows.length; i++) {
                var k = findCheckedProduct(rows[i].id);
                if (k != -1) {
                    checkedUserInfo.splice(k, 1);
                }
            }
        },
        onLoadSuccess:function(data){
            if(data.rows.length==0){
                var body = $(this).data().datagrid.dc.body2;
                body.find('table tbody').append('<tr><td width="'+body.width()+'" style="height: 25px; text-align: center;" colspan="4">没有数据</td></tr>');
            }
        },
        onClickRow: function (index, field, value) {
            document.getElementById("userName").innerHTML = field.firstName;
            document.getElementById("userCompany").innerHTML = field.company;
            document.getElementById("checkingno").innerHTML = field.checkingno;
            document.getElementById("scanNum").innerHTML = 2;
            document.getElementById("hastakeproceedStatus").innerHTML = "已领";
        }
    });
    function findCheckedProduct(id) {
        for (var i = 0; i < checkedUserInfo.length; i++) {
            if (checkedUserInfo[i] == id) return i;
        }
        return -1;
    }

    /*var fairInfoList = [];

    function getFairIndex() {
        for(var i=0,a;a=fairInfoList[i++];){
            var checkBox = document.getElementById(a.fairalairname);
            if(checkBox != null && checkBox.checked){
                return a.fairid;
            }
        }
    }

    $(function(){
        /!*$('.checkbox>input[type=checkbox]').click(function(){
            $(this).attr('checked','checked').siblings('input').removeAttr('checked');
        });*!/

        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${base}/user/loadAllEnableFairName",
            success : function(result) {
                if(result){
                    fairInfoList = result;
                    var faircheckboxhtml = document.getElementById("faircheckbox");
                    faircheckboxhtml.innerHTML = "";
                    for(var i=0,a;a=result[i++];){
                        if(i == 1) {
                            var input = document.createElement("input");
                            input.setAttribute("type", "checkbox");
                            input.setAttribute("class", "checkboxInner checkInput");
                            input.setAttribute("id", a.fairalairname);
                            input.setAttribute("checked", "checked");
                            //input.setAttribute("click", "resetcheck()");
                            input.onclick = function(){
                                $(this).attr('checked','checked').siblings('input').removeAttr('checked');
                            };
                            faircheckboxhtml.appendChild(input);
                            var label = document.createElement("label");
                            label.setAttribute("class", "checkboxInner");
                            label.innerHTML = a.fairname;
                            faircheckboxhtml.appendChild(label);
                        }else {
                            var input = document.createElement("input");
                            input.setAttribute("type", "checkbox");
                            input.setAttribute("class", "checkboxInner checkInput");
                            input.setAttribute("id", a.fairalairname);
                            input.onclick = function(){
                                $(this).attr('checked','checked').siblings('input').removeAttr('checked');
                            };
                            faircheckboxhtml.appendChild(input);
                            var label = document.createElement("label");
                            label.setAttribute("class", "checkboxInner");
                            label.innerHTML = a.fairname;
                            faircheckboxhtml.appendChild(label);
                        }
                    }
                }
            }
        });

        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${base}/user/loadAllColFieldList",
            success : function(result) {
                if(result){
                    $("#fair_name").html('');
                    for(var i=0,a;a=result[i++];){
                        $("#col_name").append('<option onclick="refreshCurrentInput()" value="' + a.colfield + '">'+a.colname+'</option>');
                    }
                    //$("#col_name").options[0].selected = true;
                }
            }
        });

        $('#scancodeText').bind('input propertychange', function() {
            var length = $(this).val().length;
            var textValue = $(this).val();
            var index = getFairIndex();
            var col_name_value = $("#col_name option:selected").val();
            if (col_name_value != "" && col_name_value == "CheckingNo" && 10 == length){
                document.getElementById("takeproceedqrcodevalue").value = textValue;
                $.ajax({
                    url: "${base}/user/queryVisitorInfoByCheckNo",
                    type: "post",
                    async : false, //同步执行
                    data: {"checkno": textValue, "databaseindex": index},
                    dataType: "json",
                    success: function (mapValue) {
                        if(mapValue.resultCode == 3 || mapValue.resultCode == 2){
                            document.getElementById("scancodeText").value = "";
                            document.getElementById("scancountText").innerText = mapValue.description;
                            document.getElementById("scancodeText").focus();
                        }else{
                            document.getElementById("scancodeText").value = "";
                            document.getElementById("scancountText").innerText = parseInt(mapValue.description);
                            document.getElementById("scancodeText").focus();
                        }

                        var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
                        if(mapValue.resultCode == 2){
                            hastakeproceedBtn.style.display = "none";
                        }else{
                            hastakeproceedBtn.style.display = "block"; //style中的display属性
                        }
                    }
                });
            }/!*else if(!$('#scanbyphone').prop('checked') && 10 == length){
                document.getElementById("takeproceedqrcodevalue").value = textValue;
                $.ajax({
                    url: "${base}/user/queryVisitorInfoByCheckNo",
                    type: "post",
                    async : false, //同步执行
                    data: {"checkno": textValue, "databaseindex": index},
                    dataType: "json",
                    success: function (mapValue) {
                        if(mapValue.resultCode == 3 || mapValue.resultCode == 2){
                            document.getElementById("scancodeText").value = "";
                            document.getElementById("scancountText").innerText = mapValue.description;
                            document.getElementById("scancodeText").focus();
                        }else{
                            document.getElementById("scancodeText").value = "";
                            document.getElementById("scancountText").innerText = parseInt(mapValue.description);
                            document.getElementById("scancodeText").focus();
                        }

                        var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
                        if(mapValue.resultCode == 2){
                            hastakeproceedBtn.style.display = "none";
                        }else{
                            hastakeproceedBtn.style.display = "block"; //style中的display属性
                        }
                    }
                });
            }*!/
        });
    });

    function refreshCurrentInput() {
        var col_name_value = $("#col_name option:selected").val();
        if(col_name_value != "" && col_name_value == "CheckingNo"){
            document.getElementById("scancodeText").setAttribute("oninput","(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)");
        }else{
            document.getElementById("scancodeText").setAttribute("oninput","");
        }
        var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
        hastakeproceedBtn.style.display = "none";
    }

    window.onload = function(){
        document.getElementById("scancodeText").focus();
    };

    /!*$(document).ready(function () {

    });*!/

    function handScanCode() {
        var fieldvalue = document.getElementById("scancodeText").value;
        var index = getFairIndex();
        var col_name_value = $("#col_name option:selected").val();
        document.getElementById("takeproceedqrcodevalue").value = fieldvalue;
        $.ajax({
            url: "${base}/user/queryCheckingNoByFieldValue",
            type: "post",
            async : false, //同步执行
            data: {"fieldvalue": fieldvalue, "databaseindex": index, "field": col_name_value},
            dataType: "json",
            success: function (mapValue) {
                if(mapValue.resultCode == 3 || mapValue.resultCode == 2){
                    document.getElementById("scancodeText").value = "";
                    document.getElementById("scancountText").innerText = mapValue.description;
                    document.getElementById("scancodeText").focus();
                }else{
                    document.getElementById("scancodeText").value = "";
                    document.getElementById("scancountText").innerText = parseInt(mapValue.description);
                    document.getElementById("scancodeText").focus();
                }

                var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
                if(mapValue.resultCode == 2){
                    hastakeproceedBtn.style.display = "none";
                }else{
                    hastakeproceedBtn.style.display = "block"; //style中的display属性
                }
            }
        });
        /!*if ($('#scanbyphone').prop('checked') && 11 == length){
            document.getElementById("takeproceedqrcodevalue").value = checknoValue;
            $.ajax({
                url: "${base}/user/queryCheckingNoByTelphoneValue",
                type: "post",
                async : false, //同步执行
                data: {"phone": checknoValue, "databaseindex": index},
                dataType: "json",
                success: function (mapValue) {
                    if(mapValue.resultCode == 3 || mapValue.resultCode == 2){
                        document.getElementById("scancodeText").value = "";
                        document.getElementById("scancountText").innerText = mapValue.description;
                        document.getElementById("scancodeText").focus();
                    }else{
                        document.getElementById("scancodeText").value = "";
                        document.getElementById("scancountText").innerText = parseInt(mapValue.description);
                        document.getElementById("scancodeText").focus();
                    }

                    var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
                    if(mapValue.resultCode == 2){
                        hastakeproceedBtn.style.display = "none";
                    }else{
                        hastakeproceedBtn.style.display = "block"; //style中的display属性
                    }
                }
            });
        }else if(!$('#scanbyphone').prop('checked') && 10 == length){
            document.getElementById("takeproceedqrcodevalue").value = checknoValue;
            $.ajax({
                url: "${base}/user/queryVisitorInfoByCheckNo",
                type: "post",
                async : false, //同步执行
                data: {"checkno": checknoValue, "databaseindex": index},
                dataType: "json",
                success: function (mapValue) {
                    if(mapValue.resultCode == 3 || mapValue.resultCode == 2){
                        document.getElementById("scancodeText").value = "";
                        document.getElementById("scancountText").innerText = mapValue.description;
                        document.getElementById("scancodeText").focus();
                    }else{
                        document.getElementById("scancodeText").value = "";
                        document.getElementById("scancountText").innerText = parseInt(mapValue.description);
                        document.getElementById("scancodeText").focus();
                    }

                    var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
                    if(mapValue.resultCode == 2){
                        hastakeproceedBtn.style.display = "none";
                    }else{
                        hastakeproceedBtn.style.display = "block"; //style中的display属性
                    }
                }
            });
        }*!/
    }

    function setHastakeproceed() {
        var value = document.getElementById("takeproceedqrcodevalue").value;
        var length = value.length;
        if ($('#scanbyphone').prop('checked') && 11 == length){
            $.ajax({
                url: "${base}/user/setHastakeproceedByTelphoneValue",
                type: "post",
                async : false, //同步执行
                data: {"phone": value},
                dataType: "json",
                success: function (mapValue) {
                    if(mapValue.resultCode == 0){
                        document.getElementById("scancountText").innerText = mapValue.description;
                        document.getElementById("scancodeText").focus();
                    }else{
                        document.getElementById("scancountText").innerText = parseInt(mapValue.description);
                        document.getElementById("scancodeText").focus();
                    }
                    var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
                    hastakeproceedBtn.style.display = "none";
                }
            });
        }else if(!$('#scanbyphone').prop('checked') && 10 == length){
            $.ajax({
                url: "${base}/user/setHastakeproceedByCheckNo",
                type: "post",
                async : false, //同步执行
                data: {"checkno": value},
                dataType: "json",
                success: function (mapValue) {
                    if(mapValue.resultCode == 0){
                        document.getElementById("scancountText").innerText = mapValue.description;
                        document.getElementById("scancodeText").focus();
                    }else{
                        document.getElementById("scancountText").innerText = parseInt(mapValue.description);
                        document.getElementById("scancodeText").focus();
                    }
                    var hastakeproceedBtn = document.getElementById("hastakeproceedBtn");
                    hastakeproceedBtn.style.display = "none";
                }
            });
        }
    }

    //搜索登记号
    $('#queryCheckingNo').click(function(){
        document.getElementById("queryCheckingNoForm").reset();
        $("#queryCheckingNoDlg").dialog("open");
    });

    // 搜索登记号弹出框
    $('#queryCheckingNoDlg').dialog({
        title: '搜索登记号',
        width: 320,
        height: 200,
        closed: true,
        cache: false,
        modal: true,
        buttons: [
            {
                text: '确认',
                iconCls: 'icon-ok',
                handler: function () {
                    if ($("#queryCheckingNoForm").form("validate")) {
                        var phoneValue = document.queryCheckingNoForm.telephone.value.trim();
                        $.ajax({
                            url: "${base}/user/queryCheckingNoByTelphoneValue",
                            type: "post",
                            dataType: "json",
                            data: {"phone": phoneValue},
                            success: function (data) {
                                $("#queryCheckingNoDlg").dialog("close");
                                if (data.resultCode == 0) {
                                    document.getElementById("scancodeText").value = data.mapData;
                                } else {
                                    $.messager.alert('错误', '查询不到对应的信息！');
                                }
                            }
                        });
                    }
                }
            },
            {
                text: '取消',
                handler: function () {
                    document.getElementById("queryCheckingNoForm").reset();
                    $("#queryCheckingNoDlg").dialog("close");
                }
            }
        ]
    });*/
</script>
</body>
</html>