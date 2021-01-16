from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.views.decorators.csrf import csrf_exempt
from users import models as user_models
from . import models as diary_models
import json
# Create your views here.

@csrf_exempt
@api_view(["GET", "POST"])
def diary(request):
    if(request.method == "POST"):
        params_json = request.body.decode(encoding = "utf-8")
        data_json = json.loads(request.body)
        uid = data_json["uid"]
        text = data_json["text"]
        is_visible = data_json["is_visible"]
        date = data_json["date"]
        try:
            user = user_models.User.objects.get(uid=uid)
            # 감정에 따라서 background 설정한 후 object 저장
            try:
                diary = diary_models.Diary.objects.get(user=user, date=date)
                # Error
                return Response("{Result:Already Exists}")
            except:
                diary = diary_models.Diary.objects.create(user=user, text=text, is_visible=is_visible, date=date)
                diary.save()
                return Response("{Result:Success}")
        except:
            # User does not exists
            return Response("{Result:Error}")

    elif(request.method == "GET"):
        uid = request.GET.get("uid")
        user = user_models.User.objects.get(uid=uid)
        diaries = diary_models.Diary.objects.filter(user=user).order_by("-created")
        # print(diaries)
        diary_serialized = []
        for d in diaries:
            diary_serialized.append(d.serialize_custom())
        # print(diary_serialized)
        return Response(diary_serialized)
    else:
        return Response("{Result:Error}")

@csrf_exempt
@api_view(["GET"])
def feeds(request):
    if(request.method == "GET"):
        diaries = diary_models.Diary.objects.filter(is_visible=True).order_by("-created")
        # print(diaries)
        diary_serialized = []
        for d in diaries:
            diary_serialized.append(d.serialize_custom())
        # print(diary_serialized)
        return Response(diary_serialized)
    else:
        return Response("{Result:Error}")

@csrf_exempt
@api_view(["GET"])
def similar_feeds(request):
    if(request.method == "GET"):
        uid = request.GET.get("uid")
        user = user_models.User.objects.get(uid=uid)
        diaries = user.diaries.order_by("-created")
        current_emotion = diaries[0].emotion
        similar_diaries = []
        if(current_emotion == diary_models.Diary.HAPPY):
            similar_diaries = diary_models.Diary.objects.filter(emotion=current_emotion,is_visible=True).order_by("-created")
        else: 
            similar_diaries = diary_models.Diary.objects.exclude(emotion=diary_models.Diary.HAPPY).filter(is_visible=True).order_by("-created")
        # similar_diaries = diary_models.Diary.objects.filter(emotion=current_emotion, is_visible=True).order_by("-created")
        diary_serialized=[]
        for diary in similar_diaries:
            diary_serialized.append(diary.serialize_custom())
        return Response(diary_serialized)
    else:
        return Response("{Result:Error}")

@csrf_exempt
@api_view(["GET"])
def opposite_feeds(request):
    if(request.method == "GET"):
        uid = request.GET.get("uid")
        user = user_models.User.objects.get(uid=uid)
        diaries = user.diaries.order_by("-created")
        current_emotion = diaries[0].emotion
        opposite_diaries = []
        if(current_emotion == diary_models.Diary.HAPPY):
            opposite_diaries = diary_models.Diary.objects.exclude(emotion=current_emotion).filter(is_visible=True).order_by("-created")
        else: 
            opposite_diaries = diary_models.Diary.objects.filter(emotion=diary_models.Diary.HAPPY, is_visible=True).order_by("-created")
        diary_serialized=[]
        for diary in opposite_diaries:
            diary_serialized.append(diary.serialize_custom())
        return Response(diary_serialized)
    else:
        return Response("{Result:Error}")
