rem ��r����PDF
set pdf1=���n��������_�X�v���b�g�e�B����2023�N3��28��.pdf
set pdf1

del diff%pdf1%

rem ��r���PDF�����A���Ԃ񂱂̃R�}���h�ōŐV��PDF���擾����͂��B
rem https://halyui.hatenablog.com/entry/2020/12/16/151512
for /f "delims=" %%i in ('dir /b /od *.pdf') do set fname=%%i
rem set fname=�V�O�F�Č�
set fname

rem ���̂Q��PDFDIFF�������邻�������Diff.pdf�Ƃ����t�@�C�����ł���B
rem https://www.kageori.com/2022/10/pdfdiff-pdf.html
diff-pdf -s --output-diff=diff%pdf1% %fname% %pdf1%
pause