# Generated by Django 3.1.5 on 2021-01-15 06:16

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('diaries', '0002_diary_user'),
    ]

    operations = [
        migrations.AddField(
            model_name='diary',
            name='date',
            field=models.CharField(default='0000-00-00', max_length=20),
        ),
    ]