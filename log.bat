	

git log --oneline > コミット履歴.txt

rem この呪文を行うにはgithubcliのインストールが必要です。 https://cli.github.com/
rem 途中でautholityの認証があったが、指示に従って楽勝だった

rem 新三芳はissue#1とした
gh issue view 1 > 新三芳履歴.txt

pause
exit