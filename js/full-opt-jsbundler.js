var fs = require('fs');
var fsExtra = require('fs-extra');

function copyHtml(webpackModule) {
    var htmlContent = fs.readFileSync(webpackModule.exports.context + '/../../../../web/index.html', 'utf-8');
    var dependencies = [];
    var match;
    var regex = /(href|src)="(.+)"/g;
    while ((match = regex.exec(htmlContent)) !== null) {
        if (match.index === regex.lastIndex) {
            regex.lastIndex++;
        }
        fsExtra.copySync(webpackModule.exports.context + "/../../../../web/"+match[2],webpackModule.exports.context + "/dist/"+match[2]);
    }
    var bodyContent = /<body>((.|\n)+)<\/body>/g.exec(htmlContent)[1];
    htmlContent = htmlContent.replace(bodyContent,bodyContent +'\n <script type="text/javascript" src="bundle.js"></script>')
    fsExtra.writeFileSync(webpackModule.exports.context + "/dist/index.html", htmlContent);
}

module.exports.apply = function (projectName, webpackModule) {
    var entryContent = 'require("core-js/es7/reflect")\nrequire("zone.js/dist/zone")';
    entryContent += fs.readFileSync(webpackModule.exports.context + '/../../'+projectName+'-sjsx.js','utf-8');
    entryContent = entryContent.replace("scalaModule","./"+projectName+"-opt.js");
    fs.writeFileSync(webpackModule.exports.context + '/'+projectName+'-sjsx.js',entryContent,'utf-8');
    copyHtml(webpackModule);
    webpackModule.exports.entry = {};
    webpackModule.exports.entry[projectName+"-opt"] = webpackModule.exports.context + '/' + projectName + '-sjsx.js';
    webpackModule.exports.output = {
        path: webpackModule.exports.context + "/dist",
        filename: "bundle.js"
    };
    webpackModule.exports.plugins = webpackModule.exports.plugins || [];
};

