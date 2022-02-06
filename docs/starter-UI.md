# uiRootTable

第1层： rootCell指定高度，自动拓展宽度，居中放storageInfoTable（其高度因此被放大或缩小）；

第2层： rootCell自动拓展高度宽度，右侧居中放gameAreaControlBoard（其可自由调整rootCell宽度，间接调整gameAreaControlBoard宽度）；

第3层： rootCell指定高度，自动拓展宽度，居中放constructionControlBoard（其高度因此被放大或缩小）；

## storageInfoTable

extends Table

其background会填满rootCell（整个第1层）

## gameAreaControlBoard

extends Table

其background会填满rootCell（整个第2层）（因此不设background）

每个innerCell指定宽度和高度（innerCell中图片因此被放大或缩小）。随Area数量不同，gameAreaControlBoard总高度也会不同。

## constructionControlBoard

extends Table

其background会填满cell（整个第3层）

ScrollPane的尺寸一定小于等于childTable，并不会因为boardCell有更多空间而拓展，故boardCell.expand时，boardCell拓展了，但ScrollPane不变。进一步地，在childTable眼里其也没有可拓展的空间（childTable检查的是ScrollPane，而不是boardCell），故对childCell.expand，连childCell都不会扩展。

当第3层高度缩小时，boardCell高度缩小，接着ScrollPane缩小出现纵向滑块，其中的childTable不缩小，保持childCell具有原始大小（特性）。强烈建议防止纵向滑块出现，为此uiRootTable指定高度时应足够大。