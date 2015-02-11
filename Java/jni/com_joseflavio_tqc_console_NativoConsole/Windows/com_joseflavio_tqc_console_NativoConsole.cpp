 
/*
 *  Copyright (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
 *  
 *  This file is part of Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 *  
 *  Jos� Fl�vio Livre is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Jos� Fl�vio Livre is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Jos� Fl�vio Livre. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  Direitos Autorais Reservados (C) 2009-2015 Jos� Fl�vio de Souza Dias J�nior
 * 
 *  Este arquivo � parte de Jos� Fl�vio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  Jos� Fl�vio Livre � software livre: voc� pode redistribu�-lo e/ou modific�-lo
 *  sob os termos da Licen�a P�blica Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a vers�o 3 da Licen�a, como
 *  (a seu crit�rio) qualquer vers�o posterior.
 * 
 *  Jos� Fl�vio Livre � distribu�do na expectativa de que seja �til,
 *  por�m, SEM NENHUMA GARANTIA; nem mesmo a garantia impl�cita de
 *  COMERCIABILIDADE ou ADEQUA��O A UMA FINALIDADE ESPEC�FICA. Consulte a
 *  Licen�a P�blica Menos Geral do GNU para mais detalhes.
 * 
 *  Voc� deve ter recebido uma c�pia da Licen�a P�blica Menos Geral do GNU
 *  junto com Jos� Fl�vio Livre. Se n�o, veja <http://www.gnu.org/licenses/>.
 */

#include <jni.h>
#include "com_joseflavio_tqc_console_NativoConsole.h"
#include <conio.h>
#include <windows.h>

JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getTotalColunas( JNIEnv *, jobject ){
    HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo( console, &csbi );
    return csbi.dwMaximumWindowSize.X;
}

JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getTotalLinhas( JNIEnv *, jobject ){
    HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo( console, &csbi );
    return csbi.dwMaximumWindowSize.Y;
}

JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_setCorTexto( JNIEnv *, jobject, jint cor ){
    
    HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo( console, &csbi );
    
    csbi.wAttributes &= 0x00F0;
    csbi.wAttributes |= cor;
    
    SetConsoleTextAttribute( console, csbi.wAttributes );
    
}

JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getCorTexto( JNIEnv *, jobject ){
    
    HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo( console, &csbi );
        
    return (jint) ( csbi.wAttributes & 0x000F );
        
}

JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_setCorTextoFundo( JNIEnv *, jobject, jint cor ){
    
    HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo( console, &csbi );
    
    csbi.wAttributes &= 0x000F;
    csbi.wAttributes |= ( cor << 4 );
    
    SetConsoleTextAttribute( console, csbi.wAttributes );
    
}

JNIEXPORT jint JNICALL Java_com_joseflavio_tqc_console_NativoConsole_getCorTextoFundo( JNIEnv *, jobject ){
    
    HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    GetConsoleScreenBufferInfo( console, &csbi );
        
    return (jint) ( ( csbi.wAttributes & 0x00F0 ) >> 4 );
    
}

JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_limpar( JNIEnv *, jobject ){

    HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
    CONSOLE_SCREEN_BUFFER_INFO csbi;
    COORD coord = {0, 0};
    DWORD chars;
    
    GetConsoleScreenBufferInfo( console, &csbi );
    DWORD tamanho = csbi.dwSize.X * csbi.dwSize.Y;
    
    FillConsoleOutputCharacter( console, TEXT(' '), tamanho, coord, &chars );
    GetConsoleScreenBufferInfo( console, &csbi );
    FillConsoleOutputAttribute( console, csbi.wAttributes, tamanho, coord, &chars );
    SetConsoleCursorPosition( console, coord );
    
}

JNIEXPORT jchar JNICALL Java_com_joseflavio_tqc_console_NativoConsole_esperar( JNIEnv *, jobject, jboolean mostrar ){
    return (jchar)( mostrar ? _getche() : _getch() );
}

JNIEXPORT void JNICALL Java_com_joseflavio_tqc_console_NativoConsole_setTelaCheia( JNIEnv *, jobject, jboolean cheia ){
    
    typedef BOOL (WINAPI *SCDMProc_t) (HANDLE, DWORD, LPDWORD);
    
    SCDMProc_t SetConsoleDisplayMode;
    HMODULE kernel32;
    BOOL freeLib = FALSE;
    const char KERNEL32_NOME[] = "kernel32.dll";
    
    kernel32 = GetModuleHandleA( KERNEL32_NOME );
    if( kernel32 == NULL ){
        kernel32 = LoadLibraryA( KERNEL32_NOME );
        if( kernel32 == NULL ) return;
        freeLib = TRUE;
    }
    
    SetConsoleDisplayMode = (SCDMProc_t) GetProcAddress( kernel32, "SetConsoleDisplayMode" );
    if( SetConsoleDisplayMode == NULL ){
        SetLastError( ERROR_CALL_NOT_IMPLEMENTED );
    }else{
        HANDLE console = GetStdHandle( STD_OUTPUT_HANDLE );
        DWORD coord;
        SetConsoleDisplayMode( console, cheia ? 1 : 0, &coord );
    }
    
    if( freeLib ) FreeLibrary( kernel32 );
    
}
