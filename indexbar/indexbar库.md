# indexbar库
该库为indexbar库,目前包含:
1.银行quickbar

该库引用的第三方包：
1. (必须)
pinyin4j-2.5.0.jar

此库因为需要用上cloud定制标题栏,所以依赖了lib_cloudbase库,后期可以移除依赖便于复用
```
BankQuickindexBar用法:
      BankIndexBarManager.init(this, new OnItemClickListener() {
                    @Override
                    public void onItemClick(BankBean datas) {
                        //datas为返回的数据
                        Toast.makeText(EditSettlementActivity.this, datas.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
```