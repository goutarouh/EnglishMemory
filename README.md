### NotionAPI使い方

#### HEADERS
- Authorization
  - Bearer secret_<API_KEY>
- Notion-Version
  - 2022-06-28

#### ページ情報を調べる
以下のURLでページ内のコンテンツ情報を取得する
https://api.notion.com/v1/pages/<PAGE_ID>

URLの最後がPAGE_IDになる。<PAGE_TITLE>がない場合あった。
https://www.notion.so/<PAGE_TITLE>-<PAGE_ID>

#### ブロック(コンテンツ)を調べる
以下のURLでページ内のコンテンツ情報を取得する
https://api.notion.com/v1/blocks/<BLOCK_ID>

コンテンツをクリックして、リンクをコピーすると取得できる
https://www.notion.so/<PAGE_TITLE>-<PAGE_ID>?#<BLOCK_ID>

#### ブロックチルドレンを調べる
ブロックを調べるAPIレスポンスのID要素を使用する
https://api.notion.com/v1/blocks/<BLOCK_CHILDREN_ID>/children?page_size=100

https://www.notion.so/6df12f0d4fce40188168817caa30660f?pvs=4#45b014c5cd1b4e389a9430f2ec3d7c1c