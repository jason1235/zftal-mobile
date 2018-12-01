 //注册一个比较大小的Helper,判断v1是否大于v2
    Handlebars.registerHelper('compare',function(v1,v2,options){
       if (v1 > v2) {
           return options.fn(this);
       }  else {
           return options.inverse(this);
       }
    });

    //注册一个比较的Helper,判断v1是否等于v2
    Handlebars.registerHelper('is',function(v1, op, v2, options){
    	if (op == "==") {
    		if ($.trim(v1) == $.trim(v2)) {
                return options.fn(this);
            }  else {
                return options.inverse(this);
            }
    	} else {
    		return options.inverse(this);
    	}
        
    });  
    Handlebars.registerPartial('pull_list_news',"{{#this}}\n  <div data-am-widget=\"list_news\" class=\"am-list-news{{#if theme}} am-list-news-{{theme}}{{else}} am-list-news-default{{/if}}{{#if widgetId}} {{widgetId}}{{/if}}{{#if className}} {{className}}{{/if}}\" {{#if id}}id=\"{{id}}\"{{/if}}>\n  <!--列表标题-->\n  {{#if content.header.title}}\n    <div class=\"am-list-news-hd am-cf\">\n      {{#if content.header.link}} <!--带更多链接-->\n        <a href=\"{{content.header.link}}\" class=\"{{content.header.className}}\">\n          <h2>{{{content.header.title}}}</h2>\n          {{#ifCond content.header.morePosition '==' 'top'}}\n            <span class=\"am-list-news-more am-fr\">{{{content.header.moreText}}}</span>\n          {{/ifCond}}\n        </a>\n      {{else}} <!--不带更多链接-->\n        <h2>{{{content.header.title}}}</h2>\n      {{/if}}\n    </div>\n  {{/if}}\n\n  <div class=\"am-list-news-bd\">\n\n  <ul class=\"am-list\" id=\"events-list\">\n  {{#ifCond options.type '==' 'thumb'}}\n    {{#ifCond options.thumbPosition '==' 'top'}} <!--缩略图在标题上方-->\n    {{#each content.main}}\n      <li class=\"am-g{{#if date}} {{/if}}{{#if desc}} am-list-item-desced{{/if}}{{#if img}} am-list-item-thumbed am-list-item-thumb-top{{/if}}\">\n        {{!--\n          am-list-item-dated - 带日期\n          am-list-item-desced - 带描述\n          am-list-item-thumbed - 带缩略图的\n        --}}\n        {{#if img}}\n        <div class=\"am-list-thumb am-u-sm-12\">\n          <a href=\"{{link}}\" class=\"{{className}}\">\n            <img src=\"{{img}}\" alt=\"{{title}}\"/>\n          </a>\n          {{#if thumbAddition}}\n            <div class=\"am-list-thumb-addon\">{{{thumbAddition}}}</div>\n          {{/if}}\n        </div>\n        {{/if}}\n\n        <div class=\"{{#if img}}{{/if}} am-list-main\">\n          {{#if title}}\n            <h3 class=\"am-list-item-hd\"><a href=\"{{link}}\" class=\"{{className}}\">{{{title}}}</a></h3>\n          {{/if}}\n\n          {{#if date}}\n            <!-- <span class=\"am-list-date\">{{date}}</span> -->\n          {{/if}}\n\n          {{#if desc}}\n            <div class=\"am-list-item-text\">{{{desc}}}</div>\n          {{/if}}\n\n          {{#if mainAddition}}\n            <div class=\"am-list-news-addon\">{{{mainAddition}}}</div>\n          {{/if}}\n        </div>\n      </li>\n    {{/each}}\n    {{/ifCond}}\n\n    {{#ifCond options.thumbPosition '==' 'bottom-left'}} <!--缩略图在标题下方居左-->\n    {{#each content.main}}\n      <li class=\"am-g{{#if date}} am-list-item-dated{{/if}}{{#if desc}} am-list-item-desced{{/if}}{{#if img}} am-list-item-thumbed am-list-item-thumb-bottom-left{{/if}}\">\n        {{!--\n          am-list-item-dated - 带日期\n          am-list-item-desced - 带描述\n          am-list-item-thumbed - 带缩略图的\n        --}}\n        {{#if title}}\n          <h3 class=\"am-list-item-hd\"><a href=\"{{link}}\" class=\"{{className}}\">{{{title}}}</a></h3>\n        {{/if}}\n        {{#if img}}\n        <div class=\"am-u-sm-4 am-list-thumb\">\n          <a href=\"{{link}}\" class=\"{{className}}\">\n            <img src=\"{{img}}\" alt=\"{{title}}\"/>\n          </a>\n          {{#if thumbAddition}}\n            <div class=\"am-list-thumb-addon\">{{{thumbAddition}}}</div>\n          {{/if}}\n        </div>\n        {{/if}}\n\n        <div class=\"{{#if img}} am-u-sm-8 {{/if}} am-list-main\">\n          {{#if date}}\n            <span class=\"am-list-date\">{{date}}</span>\n          {{/if}}\n\n          {{#if desc}}\n            <div class=\"am-list-item-text\">{{{desc}}}</div>\n          {{/if}}\n\n          {{#if mainAddition}}\n            <div class=\"am-list-news-addon\">{{{mainAddition}}}</div>\n          {{/if}}\n        </div>\n      </li>\n    {{/each}}\n    {{/ifCond}}\n\n    {{#ifCond options.thumbPosition '==' 'bottom-right'}} <!--缩略图在标题下方居右-->\n    {{#each content.main}}\n      <li class=\"am-g{{#if date}} am-list-item-dated{{/if}}{{#if desc}} am-list-item-desced{{/if}}{{#if img}} am-list-item-thumbed am-list-item-thumb-bottom-right{{/if}}\">\n        {{!--\n          am-list-item-dated - 带日期\n          am-list-item-desced - 带描述\n          am-list-item-thumbed - 带缩略图的\n        --}}\n        {{#if title}}\n          <h3 class=\"am-list-item-hd\"><a href=\"{{link}}\" class=\"{{className}}\">{{{title}}}</a></h3>\n        {{/if}}\n\n        <div class=\"{{#if img}} am-u-sm-8{{/if}} am-list-main\">\n          {{#if date}}\n            <span class=\"am-list-date\">{{date}}</span>\n          {{/if}}\n\n          {{#if desc}}\n            <div class=\"am-list-item-text\">{{{desc}}}</div>\n          {{/if}}\n\n          {{#if mainAddition}}\n            <div class=\"am-list-news-addon\">{{{mainAddition}}}</div>\n          {{/if}}\n        </div>\n        {{#if img}}\n        <div class=\"am-list-thumb am-u-sm-4\">\n          <a href=\"{{link}}\" class=\"{{className}}\">\n            <img src=\"{{img}}\" alt=\"{{title}}\"/>\n          </a>\n          {{#if thumbAddition}}\n            <div class=\"am-list-thumb-addon\">{{{thumbAddition}}}</div>\n          {{/if}}\n        </div>\n        {{/if}}\n      </li>\n    {{/each}}\n    {{/ifCond}}\n\n    {{#ifCond options.thumbPosition '==' 'left'}} <!--缩略图在标题左边-->\n    {{#each content.main}}\n      <li class=\"am-g{{#if date}} am-list-item-dated{{/if}}{{#if desc}} am-list-item-desced{{/if}}{{#if img}} am-list-item-thumbed am-list-item-thumb-left{{/if}}\">\n        {{!--\n          am-list-item-dated - 带日期\n          am-list-item-desced - 带描述\n          am-list-item-thumbed - 带缩略图的\n        --}}\n        {{#if img}}\n        <div class=\"am-u-sm-4 am-list-thumb\">\n          <a href=\"{{link}}\" class=\"{{className}}\">\n            <img src=\"{{img}}\" alt=\"{{title}}\"/>\n          </a>\n          {{#if thumbAddition}}\n            <div class=\"am-list-thumb-addon\">{{{thumbAddition}}}</div>\n          {{/if}}\n        </div>\n        {{/if}}\n\n        <div class=\"{{#if img}} am-u-sm-8{{/if}} am-list-main\">\n          {{#if title}}\n            <h3 class=\"am-list-item-hd\"><a href=\"{{link}}\" class=\"{{className}}\">{{{title}}}</a></h3>\n          {{/if}}\n          {{#if date}}\n            <span class=\"am-list-date\">{{date}}</span>\n          {{/if}}\n\n          {{#if desc}}\n            <div class=\"am-list-item-text\">{{{desc}}}</div>\n          {{/if}}\n\n          {{#if mainAddition}}\n            <div class=\"am-list-news-addon\">{{{mainAddition}}}</div>\n          {{/if}}\n        </div>\n      </li>\n    {{/each}}\n    {{/ifCond}}\n\n    {{#ifCond options.thumbPosition '==' 'right'}} <!--缩略图在标题右边-->\n    {{#each content.main}}\n      <li class=\"am-g{{#if date}} am-list-item-dated{{/if}}{{#if desc}} am-list-item-desced{{/if}}{{#if img}} am-list-item-thumbed am-list-item-thumb-right{{/if}}\">\n        {{!--\n          am-list-item-dated - 带日期\n          am-list-item-desced - 带描述\n          am-list-item-thumbed - 带缩略图的\n        --}}\n        <div class=\"{{#if img}} am-u-sm-8{{/if}} am-list-main\">\n          {{#if title}}\n            <h3 class=\"am-list-item-hd\"><a href=\"{{link}}\" class=\"{{className}}\">{{{title}}}</a></h3>\n          {{/if}}\n\n          {{#if date}}\n            <span class=\"am-list-date\">{{date}}</span>\n          {{/if}}\n\n          {{#if desc}}\n            <div class=\"am-list-item-text\">{{{desc}}}</div>\n          {{/if}}\n\n          {{#if mainAddition}}\n            <div class=\"am-list-news-addon\">{{{mainAddition}}}</div>\n          {{/if}}\n        </div>\n        {{#if img}}\n          <div class=\"am-u-sm-4 am-list-thumb\">\n            <a href=\"{{link}}\" class=\"{{className}}\">\n              <img src=\"{{img}}\" alt=\"{{title}}\"/>\n            </a>\n            {{#if thumbAddition}}\n              <div class=\"am-list-thumb-addon\">{{{thumbAddition}}}</div>\n            {{/if}}\n          </div>\n        {{/if}}\n      </li>\n    {{/each}}\n    {{/ifCond}}\n\n  {{else}}{{!--不带缩略图--}}\n    {{#each content.main}}\n      <li class=\"am-g{{#if date}} am-list-item-dated{{/if}}{{#if desc}} am-list-item-desced{{/if}}{{#if img}} am-list-item-thumbed{{/if}}\">\n        {{!--\n          am-list-item-dated - 带日期\n          am-list-item-desced - 带描述\n          am-list-item-thumbed - 带缩略图的\n        --}}\n        {{#if title}}\n          <a href=\"{{link}}\" class=\"am-list-item-hd {{className}}\">{{{title}}}</a>\n        {{/if}}\n\n        {{#if date}}\n          <span class=\"am-list-date\">{{date}}</span>\n        {{/if}}\n\n        {{#if desc}}\n          <div class=\"am-list-item-text\">{{{desc}}}</div>\n        {{/if}}\n\n        {{#if mainAddition}}\n          <div class=\"am-list-news-addon\">{{{mainAddition}}}</div>\n        {{/if}}\n      </li>\n    {{/each}}\n  {{/ifCond}}\n  </ul>\n\n<div class=\"pull-action\" id=\"pull-up\">\n            <span class=\"am-icon-arrow-down pull-label\" id=\"pull-up-label\">\n                加载更多\n            </span>\n            <span class=\"am-icon-spinner am-icon-spin\"></span>\n        </div>\n</div>\n\n  {{#ifCond content.header.morePosition '==' 'bottom'}}<!--更多在底部-->\n    {{#if content.header.link}}\n      <div class=\"am-list-news-ft\">\n        <a class=\"am-list-news-more am-btn am-btn-default {{content.header.className}}\" href=\"{{content.header.link}}\">{{{content.header.moreText}}}</a>\n      </div>\n    {{/if}}\n  {{/ifCond}}\n  </div>\n{{/this}}\n");
    Handlebars.registerPartial('pull_gallery',"{{#this}}\n<div id=\"wrapper\">\n  <ul data-am-widget=\"gallery\" class=\"am-gallery{{#if options.cols}} am-avg-sm-{{options.cols}}{{else}} am-avg-sm-2{{/if}}\n  am-avg-md-3 am-avg-lg-4 {{#if\n  theme}}am-gallery-{{theme}}{{else}}am-gallery-default{{/if}}{{#if widgetId}} {{widgetId}}{{/if}}{{#if className}} {{className}}{{/if}}\" data-am-gallery=\"{ {{#if options.gallery}}pureview: true{{/if}} }\" {{#if id}}id=\"{{id}}\"{{/if}}>\n    {{#each content}}\n      <li>\n        <div class=\"am-gallery-item\">\n          {{#if link}}\n            <a href=\"{{link}}\" class=\"{{className}}\">\n              {{#if img}}<img src=\"{{img}}\" {{#if rel}}data-rel=\"{{rel}}\"{{/if}} alt=\"{{title}}\"/>{{/if}}\n              {{#if title}}\n                <h3 class=\"am-gallery-title\">{{{title}}}</h3>\n              {{/if}}\n              {{#if desc}}\n                <div class=\"am-gallery-desc\">{{{desc}}}</div>\n              {{/if}}\n            </a>\n          {{else}}\n            {{#if img}}<img src=\"{{img}}\" {{#if rel}}data-rel=\"{{rel}}\"{{/if}} alt=\"{{title}}\"/>{{/if}}\n            {{#if title}}\n              <h3 class=\"am-gallery-title\">{{{title}}}</h3>\n            {{/if}}\n            {{#if desc}}\n              <div class=\"am-gallery-desc\">{{{desc}}}</div>\n            {{/if}}\n          {{/if}}\n        </div>\n      </li>\n    {{/each}}\n  </ul>\n<div class=\"pull-action\" id=\"pull-up\">\n            <span class=\"am-icon-arrow-up pull-label\" id=\"pull-up-label\">\n                上拉加载更多\n            </span>\n            <span class=\"am-icon-spinner am-icon-spin\"></span>\n</div>\n</div>\n{{/this}}\n");
var data = {
        header : {
            "content" : {
               
                "title" : ""

            }
        },

        navbar : {
            "className" : "testNavClass",
            "options" : {
                "cols" : 3
            },
            "content" : [
                {
                    "title" : "首页",
                    "link" : _path + "/mobile/web_index.html",
                    "icon" : "home"
                    //"customIcon" : "assets/i/tab/sy.png"
                },
                {
                    "title" : "应用中心",
                    "link" : _path + "/mobile/web_appCenter.html",
                    "icon" : "th-large"
                    //"customIcon" : "assets/i/tab/yyzx.png"
                },
                {
                    "title" : "我的门户",
                    "link" : _path + "/mobile/web_portal.html",
                    "icon" : "user"
                    //"customIcon" : "assets/i/tab/wdmh.png"
                }
                //{
                //    "title" : "交流社区",
                //    "link" : "",
                //    "icon" : "comment"
                    //"customIcon" : "assets/i/tab/wdmh.png"
               // }
            ]
        },
        gallery : {
        	options : {
        	cols : 3,
        	gallery : true
        	},
        	id : "events-list"
        },
        list_news : {
        	id : "wrapper",
            options : {
                type : "thumb",
                thumbPosition : "left"
            },
            content : {
                main: [
//                    {
//                        title: "第一栏标题",
//                        link: "http://www.zfsoft.com",
//                        date: "2015-08-18",
//                        desc: "这是描述",
//                        img: _path + "/assets/i/avatar.jpg"
//                    },
//                    {
//                        title: "第二栏标题",
//                        link: "http://www.zfsoft.com",
//                        date: "2015-08-18",
//                        desc: "这是描述",
//                        img: _path + "/assets/i/avatar.jpg"
//                    },
//                    {
//                        title: "第三栏标题",
//                        link: "http://www.zfsoft.com",
//                        date: "2015-08-18",
//                        desc: "这是描述",
//                        img: _path + "/assets/i/avatar.jpg"
//                    }
                ]
            }
        }

    };
  
 