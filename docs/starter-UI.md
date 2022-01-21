# uiRootTable

第1层： cell指定高度，自动拓展宽度，居中放storageInfoTable（其高度因此被放大或缩小）；

第2层： cell自动拓展高度宽度，右侧居中放gameAreaControlBoard（其可自由调整cell宽度，间接调整gameAreaControlBoard宽度）；

第3层： cell指定高度，自动拓展宽度，居中放constructionControlBoard（其高度因此被放大或缩小）；

## gameAreaControlBoard

extends Table

每个cell指定宽度和高度（cell中图片因此被放大或缩小）。随Area数量不同，gameAreaControlBoard总高度也会不同。

## constructionControlBoard

extends Table

当其被uiRootTable缩小高度时，是ScrollPane缩小出现纵向滑块，其中的node-cell不缩小。强烈建议防止纵向滑块出现，为此uiRootTable指定高度时应足够大。