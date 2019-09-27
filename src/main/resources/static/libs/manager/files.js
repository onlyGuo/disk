/**
 * Created by AnLuTong on 2016-12-21.
 */
$(function(){
    $(".workBtns").hide();
    $(".chickBox").click(function(){
        var isSelected = false;
        if($(this).attr("class") == "chickBox"){
            $(this).attr("class", "chickBox fa fa-check")
        }else{
            $(this).attr("class", "chickBox")
        }
        if($(this).attr("fileId") == 'all'){
            //全选
            $(".chickBox").attr("class", $(this).attr("class"));
        }

        var $box = $(".chickBox");
        for(var i = 0; i < $box.length; i++){
            if($($box[i]).attr("class") == "chickBox fa fa-check"){
                isSelected = true;
            }
        }

        if (isSelected){
            $(".workBtns").show();
        }else{
            $(".workBtns").hide();
        }
    });


    $(".fileList tbody tr").hover(function(){
        $(".fileWork").hide();
        $("#wk_" + $(this).attr("itemId")).show();
    }, function(){
        $(".fileWork").hide();
    });
    
    
    //新建文件夹
    $("#createFolder").click(function(){
    	parent.layer.prompt({title: '新建文件夹名', formType: 0}, function(text, index){
    		$.post($("#thisPage").val() + "/createFolder", {name:text}, function(response){
    			if(response.result){
    				location = location;
    				parent.layer.close(index);
    			}else{
    				parent.layer.msg(response.message);
    			}
    		}).error(function(){
    			parent.layer.msg("文件夹创建失败");
    		});
    	});
    });
    
    //上传文件
    $("#uploadFile").click(function(){
		layer.open({
			type : 2,
			title : false,
			closeBtn : 1, // 不显示关闭按钮
			shade : [ 0 ],
			area : [ '500px', '400px' ],
			offset : 'rb', // 右下角弹出
			anim : 2,
			content : [ $("#thisPage").val() + '/upload', 'yes' ], // iframe的url，no代表不显示滚动条
		});
    });
    
});

/**
 * 获取文件
 * @param fileId
 * @returns
 */
function download(fileId){
	window.open($("#rootPage").val() + '/manager/download/' + fileId); 
}

/**
 * 删除文件
 * @param fileId
 * @returns
 */
function deleteFile(fileId){
	parent.layer.load();
	
	$.get($("#rootPage").val() + '/manager/delete/' + fileId, function(response){
		if(response.result){
			parent.layer.closeAll('loading');
			parent.layer.msg("删除成功");
			location = location;
		}else{
			parent.layer.closeAll('loading');
			layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("删除遇到错误");
	});
}

/**
 * 彻底删除文件或文件夹
 * @param itemId
 * @returns
 */
function deleteItem(itemId){
	parent.layer.load();
	
	$.get($("#rootPage").val() + '/manager/deleteItem/' + itemId, function(response){
		if(response.result){
			parent.layer.closeAll('loading');
			parent.layer.msg("删除成功");
			location = location;
		}else{
			parent.layer.closeAll('loading');
			layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("删除遇到错误");
	});
}

/**
 * 删除文件夹
 * @param fileId
 * @returns
 */
function deleteFolder(fileId){
	var index = parent.layer.confirm('删除文件夹后，其内的子文件夹以及文件都将被删除。是否继续？', {
		  btn: ['继续','取消'] //按钮
		}, function(){
			//删除开始
			parent.layer.load();
			$.get($("#rootPage").val() + '/manager/delete/' + fileId, function(response){
				if(response.result){
					parent.layer.closeAll('loading');
					parent.layer.msg("删除成功");
					location = location;
				}else{
					parent.layer.closeAll('loading');
					layer.msg(response.message);
				}
			}).error(function(){
				parent.layer.closeAll('loading');
				layer.msg("删除遇到错误");
			});
			
		}, function(){
			parent.layer.close(index);
		});
}

/**
 * 分享文件
 * @param fileId
 * @returns
 */
function shareFile(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/share/' + fileId, function(response){
		parent.layer.closeAll('loading');
		parent.layer.alert(response,{
			area:['520px', '350px'],
			title:"创建分享链接"
		});
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}

/**
 * 取消分享
 * @param fileId
 * @returns
 */
function deleteShare(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/deleteShare/' + fileId, function(response){
		parent.layer.closeAll('loading');
		if(response.result){
			parent.layer.msg("已取消分享");
			location = location;
		}else{
			parent.layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}

/**
 * 创建分享密码
 * @param fileId
 * @returns
 */
function createPassword(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/createSharePswd/' + fileId, function(response){
		parent.layer.closeAll('loading');
		parent.layer.alert(response,{
			area:['520px', '350px'],
			title:"创建分享链接"
		});
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("操作遇到错误");
	});
}

function deletePassword(fileId){
	parent.layer.load();
	$.get($("#rootPage").val() + '/manager/deleteSharePassword/' + fileId, function(response){
		parent.layer.closeAll('loading');
		if(response.result){
			parent.layer.msg("已取消分享密码");
			location = location;
		}else{
			parent.layer.msg(response.message);
		}
	}).error(function(){
		parent.layer.closeAll('loading');
		layer.msg("分享遇到错误");
	});
}