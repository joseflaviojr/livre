 
/*
 *  Copyright (C) 2009-2015 José Flávio de Souza Dias Júnior
 *  
 *  This file is part of José Flávio Livre - <http://www.joseflavio.com/livre/>.
 *  
 *  José Flávio Livre is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  José Flávio Livre is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with José Flávio Livre. If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  Direitos Autorais Reservados (C) 2009-2015 José Flávio de Souza Dias Júnior
 * 
 *  Este arquivo é parte de José Flávio Livre - <http://www.joseflavio.com/livre/>.
 * 
 *  José Flávio Livre é software livre: você pode redistribuí-lo e/ou modificá-lo
 *  sob os termos da Licença Pública Menos Geral GNU conforme publicada pela
 *  Free Software Foundation, tanto a versão 3 da Licença, como
 *  (a seu critério) qualquer versão posterior.
 * 
 *  José Flávio Livre é distribuído na expectativa de que seja útil,
 *  porém, SEM NENHUMA GARANTIA; nem mesmo a garantia implícita de
 *  COMERCIABILIDADE ou ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA. Consulte a
 *  Licença Pública Menos Geral do GNU para mais detalhes.
 * 
 *  Você deve ter recebido uma cópia da Licença Pública Menos Geral do GNU
 *  junto com José Flávio Livre. Se não, veja <http://www.gnu.org/licenses/>.
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
