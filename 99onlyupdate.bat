
@ECHO O
rem githubのDrawingとつながっているフォルダです。
set path2=%userprofile%\Documents\git\\mechanical
rem path2に新たに作成するフォルダ名です。
set dname=00initial

@ECHO OFF
 
:INPUT_START
ECHO +-------------------------------------------------------+
ECHO  gitにアップロードするコメントを入力してください。
ECHO +-------------------------------------------------------+
SET INPUT_STR=
SET /P INPUT_STR=
 
IF "%INPUT_STR%"=="" GOTO :INPUT_START
 
:INPUT_CONF
ECHO +-------------------------------------------------------+
ECHO  入力した文字は[%INPUT_STR%]でよろしいですか？
ECHO （Y / N）
ECHO +-------------------------------------------------------+
SET CONF_SELECT=
SET /P CONF_SELECT=
 
IF "%CONF_SELECT%"== SET CONF_SELECT=Y
IF /I NOT "%CONF_SELECT%"=="Y"  GOTO :INPUT_START


ECHO +----githubにアップデートする処理を行います-------------+
ECHO +----コメントは-----------------------------------------+
ECHO +----[%INPUT_STR%]----+

@ECHO ON

cd %path2%
git init
git remote add origin git@github.com:shiroi36/mechanical.git
git add .
git commit -m %INPUT_STR%
git push origin master
explorer %path2%

@ECHO OFF

ECHO +-------------------終了しました------------------------+


:INPUT_END
ECHO +-------------------------------------------------------+
ECHO  完了しました。
ECHO +-------------------------------------------------------+
 
PAUSE
EXIT



