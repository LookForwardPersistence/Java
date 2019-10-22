~~~
//给jpg添加文字
    public static boolean createStringMark(String filePath,String markContent,String outPath)
    {
        ImageIcon imgIcon=new ImageIcon(filePath);
        Image theImg =imgIcon.getImage();
        int width=theImg.getWidth(null)==-1?200:theImg.getWidth(null);
        int height= theImg.getHeight(null)==-1?200:theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g=bimage.createGraphics();

        Color mycolor = Color.black;
        g.setColor(mycolor);
        g.setBackground(Color.black);
        g.drawImage(theImg, 0, 0, null );
        g.setFont(new Font("宋体",Font.PLAIN,16)); //字体、字型、字号
        g.drawString(markContent,8,18); //画文字
        g.dispose();
        try
        {
            FileOutputStream out=new FileOutputStream(outPath); //先用一个特定的输出文件名
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
            param.setQuality(100, true);  //
            encoder.encode(bimage, param);
            out.close();
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    public void sendMessage() throws MessagingException {
        Properties properties = new Properties();
        // properties.put("mail.smtp.host", "mailcas.chinapnr.com");// 设置smtp主机
        properties.put("mail.smtp.host", "mail.csot.tcl.com");// 设置smtp主机
        properties.put("mail.smtp.auth", "true");// 使用smtp身份验证
		/*
		 * 在 JavaMail 中，可以通过 extends Authenticator 抽象类，在子类中覆盖父类中的
		 * getPasswordAuthentication() 方法，就可以实现以不同的方式来进行登录邮箱时的用户身份认证。JavaMail
		 * 中的这种设计是使用了策略模式（Strategy
		 */
        MimeMessage message = new MimeMessage(Session.getInstance(properties,
                new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(//设置发送帐号密码
                                "csot.szportal", "csot.999");
                    }
                }));
        // 设置邮件的属性
        // 设置邮件的发件人
        message.setFrom(new InternetAddress("csot.szportal@tcl.com"));
        // 设置邮件的收件人 cc表示抄送 bcc 表示暗送
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                "fanqianghua@tcl.com"));
        // 设置邮件的主题
        message.setSubject("邮件通知");
        // 创建邮件的正文
        MimeBodyPart text = new MimeBodyPart();
//        // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
        text.setContent("<img src='cid:a'>", "text/html;charset=gb2312");
        // 创建图片
        MimeBodyPart img = new MimeBodyPart();

        //		 * JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
        //		 * 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的. JavaMail
        //		 * API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.
        DataHandler dh = new DataHandler(new FileDataSource("src//main//resources//images//attendanceMaster.jpg"));//图片路径
        img.setDataHandler(dh);
        // 创建图片的一个表示用于显示在邮件中显示
        img.setContentID("a");
        // 关系 正文和图片的
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
        mm.addBodyPart(img);
        mm.setSubType("related");// 设置正文与图片之间的关系 mixed related

        message.setContent(mm);
        message.saveChanges(); // 保存修改
        Transport.send(message);// 发送邮件
        System.out.println("邮件发送成功");
    }
    public static void main(String[] args) throws MessagingException {
        String content="您于2017年1月16日入职，距离今天已经960天，续签合同1次~~~~~";
        createStringMark("src//main//resources//images//attendance.jpg", content,"src//main//resources//images//attendanceMaster.jpg");
        WaterMark waterMark = new WaterMark();
        waterMark.sendMessage();
    }

~~~
