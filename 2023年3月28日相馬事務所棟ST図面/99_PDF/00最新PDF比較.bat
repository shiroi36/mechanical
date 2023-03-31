rem 比較元のPDF
set pdf1=相馬事務所棟_スプリットティ検討2023年3月28日.pdf
set pdf1

del diff%pdf1%

rem 比較先のPDFだが、たぶんこのコマンドで最新のPDFを取得するはず。
rem https://halyui.hatenablog.com/entry/2020/12/16/151512
for /f "delims=" %%i in ('dir /b /od *.pdf') do set fname=%%i
rem set fname=新三芳案件
set fname

rem この２つでPDFDIFFをかけるそうするとDiff.pdfというファイルができる。
rem https://www.kageori.com/2022/10/pdfdiff-pdf.html
diff-pdf -s --output-diff=diff%pdf1% %fname% %pdf1%
pause