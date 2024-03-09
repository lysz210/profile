const fs = require("fs")
module.exports = function (content, map, meta) {
    console.log("loader pdf", ">>>>>map", map, ">>>>>>meta", meta)
    return "hello"
}