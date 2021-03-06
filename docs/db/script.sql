USE [master]
GO
/****** Object:  Database [sa]    Script Date: 01/03/2021 11:56:40 ******/
CREATE DATABASE [sa] ON  PRIMARY 
( NAME = N'sa', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL10_50.SQLEXPRESS\MSSQL\DATA\sa.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'sa_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL10_50.SQLEXPRESS\MSSQL\DATA\sa_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [sa] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [sa].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [sa] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [sa] SET ANSI_NULLS OFF
GO
ALTER DATABASE [sa] SET ANSI_PADDING OFF
GO
ALTER DATABASE [sa] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [sa] SET ARITHABORT OFF
GO
ALTER DATABASE [sa] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [sa] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [sa] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [sa] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [sa] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [sa] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [sa] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [sa] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [sa] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [sa] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [sa] SET  DISABLE_BROKER
GO
ALTER DATABASE [sa] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [sa] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [sa] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [sa] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [sa] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [sa] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [sa] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [sa] SET  READ_WRITE
GO
ALTER DATABASE [sa] SET RECOVERY SIMPLE
GO
ALTER DATABASE [sa] SET  MULTI_USER
GO
ALTER DATABASE [sa] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [sa] SET DB_CHAINING OFF
GO
USE [sa]
GO
/****** Object:  Table [dbo].[document]    Script Date: 01/03/2021 11:56:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[document](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[update_time] [datetime] NULL,
	[category_id] [int] NULL,
	[category_name] [nvarchar](255) NULL,
	[title] [nvarchar](255) NULL,
	[content] [text] NULL,
 CONSTRAINT [PK_document] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document', @level2type=N'COLUMN',@level2name=N'create_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document', @level2type=N'COLUMN',@level2name=N'update_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类别id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document', @level2type=N'COLUMN',@level2name=N'category_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类别名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document', @level2type=N'COLUMN',@level2name=N'category_name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'标题' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document', @level2type=N'COLUMN',@level2name=N'title'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'内容' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document', @level2type=N'COLUMN',@level2name=N'content'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'文档' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'document'
GO
/****** Object:  Table [dbo].[category]    Script Date: 01/03/2021 11:56:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[update_time] [datetime] NULL,
	[name] [nvarchar](255) NULL,
	[parent_id] [int] NULL,
 CONSTRAINT [PK_category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'主键id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'category', @level2type=N'COLUMN',@level2name=N'id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'category', @level2type=N'COLUMN',@level2name=N'create_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'修改时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'category', @level2type=N'COLUMN',@level2name=N'update_time'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'category', @level2type=N'COLUMN',@level2name=N'name'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'父级id' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'category', @level2type=N'COLUMN',@level2name=N'parent_id'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'类别' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'category'
GO
/****** Object:  Default [DF_document_create_time]    Script Date: 01/03/2021 11:56:40 ******/
ALTER TABLE [dbo].[document] ADD  CONSTRAINT [DF_document_create_time]  DEFAULT (getdate()) FOR [create_time]
GO
/****** Object:  Default [DF_category_create_time]    Script Date: 01/03/2021 11:56:40 ******/
ALTER TABLE [dbo].[category] ADD  CONSTRAINT [DF_category_create_time]  DEFAULT (getdate()) FOR [create_time]
GO
/****** Object:  Default [DF_category_parent_id]    Script Date: 01/03/2021 11:56:40 ******/
ALTER TABLE [dbo].[category] ADD  CONSTRAINT [DF_category_parent_id]  DEFAULT ((0)) FOR [parent_id]
GO
