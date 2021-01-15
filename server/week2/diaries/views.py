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
        percent = data_json["percent"]
        text = data_json["text"]
        emotion = data_json["emotion"]
        try:
            user = user_models.User.objects.get(uid=uid)
            # 감정에 따라서 background 설정한 후 object 저장
        except:
            # User does not exists
            return Response("{Result:Error}")

    elif(request.method == "GET"):
        print("GET")

@csrf_exempt
@api_view(["GET"])
def feeds(request):
    if(request.method == "GET"):
        diaries = diary_models.Diary.objects.all().order_by("-created")
        # print(diaries)
        diary_serialized = []
        for d in diaries:
            diary_serialized.append(d.serialize_custom())
        # print(diary_serialized)
        return Response(diary_serialized)
    else:
        return Response("{Result:Error}")