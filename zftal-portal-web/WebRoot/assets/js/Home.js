/**
 * Created by Administrator on 15-9-11.
 * 资讯首页展示
 */


"use strict";
//var _path = '<%=request.getContextPath()%>';
//
//Handlebars.registerHelper('is',function(v1, op, v2, options){
//    if (op == "==") {
//        if ($.trim(v1) == $.trim(v2)) {
//            return options.fn(this);
//        }  else {
//            return options.inverse(this);
//        }
//    } else {
//        return options.inverse(this);
//    }
//
//});
if (!data.hasOwnProperty('gallery')) {
    data.gallery = {}
}
var Mobile = {};
var Home = function (obj) {
    this.init(obj);

    return this;
};

Home.prototype = {
    config: {
        template: "",
        showHeader : true,
        showFooter: true,
        showMenu : true,
        headerTemplate : null, //头部模板
        contentTemplate : null, // 内容模板
        navTemplate : null, // 底部导航模板
        menuTemplate : null, // 菜单模板
        contentType : "list", // 内容展示形式：列表(list)，图片(gallery)，详情(detail)。默认是列表
        showType : null,
        container: "#main", // 容器
        userData : null, // 用户数据源
        data : null // 预配置数据
    },

    httpRegEx : /[(http)|(https)]:.*/i,

    isPc : function() {
        var agent = navigator.userAgent.toLowerCase();
        var patterns = ["android","iphone","windows phone","windows ce","ipad","ipod","rv:1.2.3.4","ucweb"];
        for (var i = 0; i < patterns.length; i++) {
            if (agent.indexOf(patterns[i]) > -1) {
                return false;
            }
        }
        return true;
    },

    /**
     * 编译模板
     * @param tmp 模板
     * @param data 数据源
     * @returns {*}
     */
    compile : function(tmp,data) {
        var template = Handlebars.compile(tmp);
        return template(data);
    },

    /**
     * 配置数据源
     * @param comp 组件
     *             资讯列表list_news -> data.list_news.content.main
     *             图片gallery -> data.gallery.content
     * @param userData
     */
    configData : function(userData) {
        if (!userData || typeof  userData == "string") {
            return;
        }
        var data = this.config.data;
        //var main = data.list_news.content.main;
        var main = [];
        if (userData instanceof Array) {
            for (var i = 0,len = userData.length; i < len; i++) {
                main.push(this.buildMainObj(userData[i]));
            }
        } else {
            main.push(this.buildMainObj(userData));
        }
        var contentType = this.config.contentType;
        if (contentType == "list") {
            data.list_news.content.main = main;
            if (this.config.listType == "2") {
            	data.list_news.options.thumbPosition = 'top';
            } else {
            	data.list_news.options.thumbPosition = 'left';
            }
        } else if (contentType == "gallery") {
            data.gallery.content = main;
        } else {
            data.main = main;
        }

    },

    /**
     * 将用户传入的数据源转化为标准数据源
     * @param userData
     * @returns {Array}
     */
    transformToStandardArray : function(userData) {
        var main = [];
        if (!userData || typeof  userData == "string") {
            return main;
        }
        if (userData instanceof Array) {
            for (var i = 0,len = userData.length; i < len; i++) {
                main.push(this.buildMainObj(userData[i]));
            }
        } else {
            main.push(this.buildMainObj(userData));
        }

        return main;

    },

    /**
     * 渲染组件
     * @param tmp 模板
     * @param container 容器
     * @param isReplace 是否替换
     */
    renderComp : function(tmp,container,isReplace) {
        if (isReplace) {
            $(container).html(this.compile(tmp,this.config.data));
        } else {
            $(container).append(this.compile(tmp,this.config.data));
        }
        if (this.config.contentType == 'detail') {
        	var news = this.config.data.news;
        	$('#article-title').html(news.title);
        	$('#article-author').html(news.author);
        	$('#article-intro').html(news.intro);
        	$('#article-content').html(news.content);
        }
    },

    /**
     * 头部模板
     */
    getHeaderTemplate : function() {
     if (this.config.headerTemplate) {
         return this.config.headerTemplate;
     } else {
         return "{{>header header}}";
     }
    },

    /**
     * 菜单模板
     */
    getMenuTemplate : function() {
        if (this.config.menuTemplate) {
            return this.config.menuTemplate;
        } else {
        	return this.menuTemplate.join('');
//            return [
//                '<div id="menu-tab" style="height:3rem;position:fixed;width:100%;background:#fff;z-index:9999;">',
//                '<div class="am-scrollable-horizontal" style="width:90%;float:left;">',
//                '<table class="am-table  am-text-nowrap" style="margin-bottom:0">',
//                '<tbody>',
//                '<tr>',
//                '{{#each this.menuInfo}}',
//                '{{#is this.catalog \'==\' ../currentCatalog}}',
//                //'<td class="td-focus"><a href="'+_path+'/mobile/web_index.html?catalogId={{this.catalog}}&type={{this.type}}&listType={{this.listType}}" data-user-menu="{catalogId:\'{{catalog}}\',type:\'{{type}}\',listType:\'{{listType}}\'}">{{this.name}}</a></td>',
//                '<td class="td-focus"><a href="javascript:void(0)" onclick="selectMenu(\'{{this.webUrl}}\')">{{this.name}}</a></td>',
//                '{{else}}',
//                //'<td class=""><a href="'+_path+'/mobile/web_index.html?catalogId={{this.catalog}}&type={{this.type}}&listType={{this.listType}}" data-user-menu="{catalogId:\'{{catalog}}\',type:\'{{type}}\',listType:\'{{listType}}\'}">{{this.name}}</a></td>',
//                '<td class=""><a href="javascript:void(0)" onclick="selectMenu(\'{{this.webUrl}}\')">{{this.name}}</a></td>',
//                '{{/is}}',
//                '{{/each}}',
//                '</tr>',
//                '</tbody>',
//                '</table>',
//                '</div>',
//                '<a id="showSub" href="'+_path+'/mobile/web_subscribe.html" class="am-icon-list" style="float:left;cursor:pointer;color:#0e90d2;line-height:3.5rem;text-align:center;margin-left:0.5rem"></a>',
//                '</div>',
//            ].join("");
        }
    },
    
    menuTemplate : [
                    '<div style="background:#fff;width:100%"><div id="menuWrapper"><div id="menuScroller"><ul>',
                    '{{#each this.menuInfo}}',
                    '{{#is this.catalog \'==\' ../currentCatalog}}',
                    '<li data-catalog="{{this.catalog}}" class="li-focus"><a href="javascript:void(0)" class="am-text-sm" style="line-height:3rem;" onclick="selectMenu(\'{{this.webUrl}}\')">{{this.name}}</a></li>',
                    '{{else}}',
                    '<li data-catalog="{{this.catalog}}"><a href="javascript:void(0)" class="am-text-sm" style="line-height:3rem;" onclick="selectMenu(\'{{this.webUrl}}\')">{{this.name}}</a></li>',
                    '{{/is}}',
                    '{{/each}}',
                    '</ul></div>',
                    '</div>',
                    '<a id="showSub" href="'+_path+'/mobile/web_subscribe.html" class="am-icon-list" style="position:fixed;left:90%;z-index:999;width:10%;background-color:#fff;line-height:3rem;cursor:pointer;color:#0e90d2;text-align:center;"></a>',
                    '</div>'
                    ], 

                    
    getContentTemplate : function() {
        if (this.config.contentTemplate) {
            return this.config.contentTemplate;
        } else {
            var contentType = this.config.contentType;
            if (contentType == "list") {
                return '<div id="content">{{>pull_list_news list_news}}</div>';
            } else if (contentType == "gallery") {
                return '<div id="content">{{>gallery gallery}}</div>';
            } else {
                return '<div id="content">' + this.articleTemplate() + '</div>';
            }

        }
    },

    getPureContentTemplate : function() {
        if (this.config.contentTemplate) {
            return this.config.contentTemplate;
        } else {
            var contentType = this.config.contentType;
            if (contentType == "list") {
                return '{{>pull_list_news list_news}}';
            } else if (contentType == "gallery") {
                return '{{>gallery gallery}}';
            } else {
                return this.articleTemplate();
            }

        }
    },

    getNavTemplate : function() {
        if (this.config.navTemplate) {
            return this.config.navTemplate;
        } else {
            return "{{>navbar navbar}}";
        }
    },

    articleTemplate : function(){
      return  [
        // '<button class="am-btn am-btn-primary am-btn-sm am-round" onclick="javascript:history.go(-1)">返回</button>',      
        '<article class="am-article" style="display:block;">',
            '<div class="am-article-hd">',
            '<h1 class="am-article-title" id="article-title"></h1>',
            '<p class="am-article-meta" id="article-author"></p>',
            '</div>',
            '<div class="am-article-bd">',
            '<p class="am-article-lead" id="article-intro"></p>',
            '<p id="article-content" id="article-content"></p>',
            //'<script>$("#article-content").html("{{this.news.content}}")</script>',
            '</div>',
            '</article>'
      ].join("");
    },

    buildMainObj : function(userDataEntity) {
        var obj = {};
        obj.title = userDataEntity.title;
        //obj.link = _path + "/mobile/web_content.html?newsId=" + userDataEntity.id;
        var logoPath = userDataEntity.logopath;
        if (logoPath) {
            if (this.httpRegEx.test(logoPath)) {
                obj.img = logoPath;
            } else {
            	var images = this.resolveJitImagePath(logoPath);
            	if (images.length > 0) {
            		obj.img = images[0]
            	} else {
            		obj.img = _path + "/upload/" + logoPath;
            	}
                
            }
        } else {
            obj.img = "";
        }
        if (this.config.contentType == 'gallery') {
            obj.link = obj.img;
        } else {
            //obj.link = _path + "/mobile/web_content.html?newsId=" + userDataEntity.id;
            obj.link = _path + "/mobile/web_content.html?newsId=" + userDataEntity.id;
        }
        //obj.date = userDataEntity.timecreate;
        return obj;
    },
    
    resolveJitImagePath : function(imageName) {
    	if (imageName == null || imageName == '') {
    		return [];
    	}
    	var imageArray = [];
    	var prefix = 'http://www.jit.edu.cn/files/UploadedImages/';
    	var reg = /([0-9]{4})([0-9]{2})[0-9]+\.jpg/i;
    	var images = imageName.split(';');
    	for (var i = 0,len = images.length; i < len; i++) {
    		var image = images[i];
    		var ret = reg.exec(image);
    		if (ret != null) {
    			var cp = prefix + ret[1] + '-' + parseInt(ret[2]) + '/' + image;
    			imageArray.push(cp);
    		}
    	}
    	return imageArray;
    	
    },

    init: function (obj) {
        if (obj) {
            $.extend(this.config, obj);
        }
        this.setContentType();
        // 配置数据
        this.configData(this.config.userData);
        // 渲染头部
        if (this.isPc() && this.config.showHeader) {
        	this.renderComp(this.getHeaderTemplate(),this.config.container);
        }
        // 渲染菜单
        if (this.config.showMenu) {
            this.renderComp(this.getMenuTemplate(),this.config.container);
        }
        // 渲染内容
        this.renderComp(this.getContentTemplate(),this.config.container);
        // 渲染底部导航栏
        if (this.config.showFooter) {
        	this.renderComp(this.getNavTemplate(),this.config.container);
        }
//        var wrapperHeight = $(document).height() - 42-49;
//        console.log('wrapperHeight : ' + wrapperHeight + ",height : " + $(document).height());
//        if (this.isPc()) {
//        	$('#wrapper').css('top','9.1rem');
//        	$('#wrapper').css('height',(wrapperHeight-49) + 'px');
//        } else {
//        	$('#wrapper').css('top','4.1rem');
//        	$('#wrapper').css('height',wrapperHeight + 'px');
//        }
        // 注册事件
        //this.addEvent();
        if (!this.isPc()) {
        	//$('#menu-tab').css('top','0');
        	$('#menuWrapper').css('top','0');
        	$('#showSub').css('top','0');
        }
        
        var myScroll = new $.AMUI.iScroll('#menuWrapper',{scrollX:true,scrollY:false,click:true});
        myScroll.scrollToElement($('li[data-catalog="'+this.config.data.currentCatalog+'"]').get(0));
    	this.start = 1;
    	this.total = this.config.total;
    	var _this = this;
    	$(window).scroll(function(){
    		if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
    			$('#pull-up').addClass('loading');
    			_this.loadMoreList();
    		}
    	});
        
    },
    
    setContentType : function() {
    	if (this.config.showType) {
    		if (this.config.showType == 'DETAIL_SHOW') {
    			this.config.contentType = 'detail';
    		} else if (this.config.showType == 'IMAGE_SHOW') {
    			this.config.contentType = 'gallery';
    		} else {
    			this.config.contentType = 'list';
    		}
    	}
    },

    addEvent : function() {
        var elem = $(document).find('[data-user-menu]');
        var that = this;
        elem.on('click',function(){
            var attr = eval("(" + $(this).attr('data-user-menu') + ")");
            var url = _path + "/mobile/web_list.html"
            if (attr) {
                url += "?catalogId=" + attr.catalogId + "&type=" + attr.type + "&listType=" + attr.listType;
                if (attr.type == 'DETAIL_SHOW') {
                    that.config.contentType = "detail";
                } else if (attr.type == 'IMAGE_SHOW') {
                    that.config.contentType = 'gallery';
                } else {
                    that.config.contentType = "list";
                }
                if (attr.listType == 2) {
                    that.config.data.list_news.options.thumbPosition = 'top';
                } else {
                    that.config.data.list_news.options.thumbPosition = 'left';
                }
            }
            //$('#content').html(url);
            $.get(url,function(ret) {
            	var result = JSON.parse(ret);
                if (that.config.contentType != 'detail') {
                    that.configData(result.news);
                    that.renderComp(that.getPureContentTemplate(),"#content",true);
                } else {
                	var news = result[0];
                	//that.configData(that.config.data);
                	
                	that.renderComp(that.getPureContentTemplate(),"#content",true);
                	$('#article-title').html(news.title);
                	$('#article-author').html(news.author);
                	$('#article-intro').html(news.intro);
                	$('#article-content').html(news.content);
                	
                }
                
            });
        });

    },
    
    escape2Html : function(str) {
    	 var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
    	 return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
    },

    galleryListTemplate: [
        '{{#each this}}',
        '<li>',
        '<div class="am-gallery-item">',
        '<a href="{{img}}" class="">',
        '<img src="{{img}}"  alt="{{title}}"/>',
        '<h3 class="am-gallery-title">{{title}}</h3>',
            '<div class="am-gallery-desc">{{date}}</div>',
        '</a>',
        '</div>',
        '</li>',
        '{{/each}}'
    ].join(""),
    
    newsListTemplateDefault : [
                        '{{#each this}}',
                        '<li class="am-g am-list-item-desced am-list-item-dated">',
                        	'<a href="{{link}}" class="am-list-item-hd ">{{title}}</a>',
                        	'<span class="am-list-date">{{date}}</span>',
                        '</li>',
                        '{{/each}}'
    ].join(""),

    newsListTemplateLeft : [
        '{{#each this}}',
        '<li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-left">',
            '<div class="am-u-sm-4 am-list-thumb">',
            '<a href="{{link}}" class="">',
            '<img src="{{img}}" alt="{{title}}"/>',
            '</a>',
            '</div>',
            '<div class=" am-u-sm-8 am-list-main">',
            '<h3 class="am-list-item-hd"><a href="{{link}}" class="">{{title}}</a></h3>',
            '<span class="am-list-date" style="position:absolute">{{date}}</span>',
        '</div>',
        '</li>',
        '{{/each}}'
    ].join(""),

    newsListTemplateTop : [
        '{{#each this}}',
        '<li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-top">',
        '<div class="am-u-sm-12 am-list-thumb">',
        '<a href="{{link}}" class="">',
        '<img src="{{img}}" alt="{{title}}"/>',
        '</a>',
        '</div>',
        '<div class="am-list-main">',
        '<h3 class="am-list-item-hd"><a href="{{link}}" class="">{{title}}</a></h3>',
        '<div class="am-list-item-text">{{desc}}</div>',
        '</div>',
        '</li>',
        '{{/each}}'
    ].join(""),

    loadMore: function (parent, moreData, type) {
        var tmp = "",
            contentType = this.config.contentType,
            position = this.config.data.list_news.options.thumbPosition;
        var moreDataArray = this.transformToStandardArray(moreData);
        if (contentType == "list") {
            if (position == 'left') {
                tmp = this.newsListTemplateLeft;
            } else if (position == 'top') {
                tmp = this.newsListTemplateTop;
            } else {
            	tmp = this.newsListTemplateDefault;
            }
        } else if (contentType == "gallery") {
            tmp = this.galleryListTemplate;
        }
        var html = this.compile(tmp,moreDataArray);
        if (type === 'refresh') {
        	parent.children('li').first().before(html);
        } else if (type === 'load') {
        	parent.append(html);
        } else {
        	parent.html(html);
        }
    },
    
    loadMoreList : function() {
    	var _this = this;
    	this.start++;
    	if (this.start <= this.total) {
    		var url = _path + "/mobile/web_list.html?catalogId=" + this.config.data.currentCatalog + "&type=" + this.config.showType + "&start=" + this.start + "&size=10";
    		$.get(url,function(ret) {
    			var result = eval("(" + ret + ")");
    			_this.loadMore($('.am-list'), result.news, 'load');
    			_this.start++;
    			_this.total = result['@pagesize'];
    			$('#pull-up').removeClass('loading');
    		});
    	} else {
    		$('#pull-up').removeClass('loading');
    	}
    	
    	
    	
    },

    selectTab : function(index) {
        $('.am-navbar-nav li:eq('+index+') a').css('color','#1157AA');
    }
};

Mobile.Home = Home;


