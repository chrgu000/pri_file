using CodeSmith.Engine;
using SchemaExplorer;
using System;
using System.Windows.Forms.Design;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Globalization;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;
using System.IO;
using System.Xml;
using System.Xml.Serialization;

namespace LiWenLiang
{
	/// <summary>
	/// 
	/// </summary>
	[DefaultProperty("ChooseSourceDatabase")]
	public class CreateCode : CodeTemplate
	{
		public CodeTemplate CompileTemplate(string templateName)
		{	
			CodeTemplateCompiler compiler = new CodeTemplateCompiler(templateName);
			compiler.Compile();	
		
			if (compiler.Errors.Count == 0)
			{
				return compiler.CreateInstance();
			}
			else
			{
				return null;
			}
		
		}
		
		public static string GetParameterNameList(ColumnSchemaCollection columns)
		{
			string str="";
			foreach(ColumnSchema col in columns)
			{
				str += "@" + col.Name + ",";
			}
			str = str.TrimEnd(',');
			return str;
		}
		
		public static string HAHA()
		{
			return "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈";
		}
		
		public static string GetParameterList(ColumnSchemaCollection columns)
		{
			string str = "";
			
			foreach (ColumnSchema col in columns) {
				str += "new SqlParameter(\"@" + col.Name +"\", SqlDbType." + GetSqlDbType(col) + "),\n                ";
				}
				
			str = str.TrimEnd(',',' ','\n');
			return str;			
		}
		
		public static string GetUpdateScript(ColumnSchemaCollection columns)
		{
			string str = "";
			
			foreach (ColumnSchema col in columns) {
				
				str += string.Format("{0} = @{0},", col.Name);
				}
				
			str = str.TrimEnd(',',' ','\n');
			return str;			
		}
		//获得C＃对应的数据类型
		public static string GetSqlDbType(DataObjectBase column)	
		{
			switch (column.NativeType)
			{
				case "bigint": return "BigInt";
				case "binary": return "Binary";
				case "bit": return "Bit";
				case "char": return "Char";
				case "datetime": return "DateTime";
				case "decimal": return "Decimal";
				case "float": return "Float";
				case "image": return "Image";
				case "int": return "Int";
				case "money": return "Money";
				case "nchar": return "NChar";
				case "ntext": return "NText";
				case "numeric": return "Decimal";
				case "nvarchar": return "NVarChar";
				case "real": return "Real";
				case "smalldatetime": return "SmallDateTime";
				case "smallint": return "SmallInt";
				case "smallmoney": return "SmallMoney";
				case "sql_variant": return "Variant";
				case "sysname": return "NChar";
				case "text": return "Text";
				case "timestamp": return "Timestamp";
				case "tinyint": return "TinyInt";
				case "uniqueidentifier": return "UniqueIdentifier";
				case "varbinary": return "VarBinary";
				case "varchar": return "VarChar";
				default: return "__UNKNOWN__" + column.NativeType;
			}
		}
		//获得DataReader时对应的数据转换方法方法
		public static string GetReaderConvertType(ColumnSchema column)
		{
			switch (column.DataType)
			{
				case DbType.Byte:
				{
					return "GetByte";
				}
				case DbType.Int16:
				{
					return "GetInt16";
				}
				case DbType.Int32:
				{
					return "GetInt32";
				}
				case DbType.Int64:
				{
					return "GetInt64";
				}
				case DbType.AnsiStringFixedLength:
				case DbType.AnsiString:
				case DbType.String:
				case DbType.StringFixedLength:
				{
					return "GetString";
				}
				case DbType.Boolean:
				{
					return "GetBoolean";
				}
				case DbType.Guid:
				{
					return "GetGuid";
				}
				case DbType.Currency:
				case DbType.Decimal:
				{
					return "GetDecimal";
				}
				case DbType.DateTime:
				case DbType.Date:
				{
					return "GetDateTime";
				}
				case DbType.Binary:
				{
					return "GetBytes";
				}
				default:
				{
					return "__SQL__" + column.DataType;
				}
			}
		}
		public static string GetCSType(ColumnSchema column)
		{
			if (column.Name.EndsWith("TypeCode")) return column.Name;
			
			switch (column.DataType)
			{
				case DbType.AnsiString: return "string";
				case DbType.AnsiStringFixedLength: return "string";
				case DbType.Binary: return "byte[]";
				case DbType.Boolean: return "bool";
				case DbType.Byte: return "byte";
				case DbType.Currency: return "decimal";
				case DbType.Date: return "DateTime";
				case DbType.DateTime: return "DateTime";
				case DbType.Decimal: return "decimal";
				case DbType.Double: return "double";
				case DbType.Guid: return "Guid";
				case DbType.Int16: return "short";
				case DbType.Int32: return "int";
				case DbType.Int64: return "long";
				case DbType.Object: return "object";
				case DbType.SByte: return "sbyte";
				case DbType.Single: return "float";
				case DbType.String: return "string";
				case DbType.StringFixedLength: return "string";
				case DbType.Time: return "TimeSpan";
				case DbType.UInt16: return "ushort";
				case DbType.UInt32: return "uint";
				case DbType.UInt64: return "ulong";
				case DbType.VarNumeric: return "decimal";
				default:
				{
					return "__UNKNOWN__" + column.NativeType;
				}
			}
		}
		public static string copyright()
		{
			string s = string.Format("//Design by:{0}({1}/{2})","李文亮","12236568@qq.com",DateTime.Now.ToShortDateString());
			return s;
		}
    }
}