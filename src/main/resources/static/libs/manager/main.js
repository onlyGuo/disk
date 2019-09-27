/**
 * Created by AnLuTong on 2016-12-21.
 */

$(function(){
//	$("#yiBiao").disk("poll1",{
//		title:'硬盘使用情况',
//		//titleColor:'#ff6600',
//		width:'100%',
//		data:{
//			total:'100M',users:'50M',peruser:'50%',disks:[
//				{id:'10000',name:'总容量',value:'50',total:'100M'}
//			]
//		}
//	});	
    
    
    $(".nav li").click(function(){
        $(".nav li").attr("class", "");
        var $this = $(this);
        $this.attr("class", "active");
        $("#workIframe").attr("src", $this.attr("addr"))
    });
    
});